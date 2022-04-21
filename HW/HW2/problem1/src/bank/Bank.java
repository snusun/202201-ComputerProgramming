package bank;

import bank.event.*;
import security.*;
import security.key.*;

public class Bank {
    private int numAccounts = 0;
    final static int maxAccounts = 100;
    private BankAccount[] accounts = new BankAccount[maxAccounts];
    private String[] ids = new String[maxAccounts];

    public void createAccount(String id, String password) {
        //TODO: Problem 1.1
        if (find(id) != null) return;
        BankAccount bankAccount = new BankAccount(id, password, 0);
        accounts[numAccounts] = bankAccount;
        ids[numAccounts] = id;
        numAccounts++;
    }

    public void createAccount(String id, String password, int initBalance) {
        //TODO: Problem 1.1
        if (find(id) != null) return;
        BankAccount bankAccount = new BankAccount(id, password, initBalance);
        accounts[numAccounts] = bankAccount;
        ids[numAccounts] = id;
        numAccounts++;
    }

    public boolean deposit(String id, String password, int amount, int transId) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if (account == null) return false;
        if (account.authenticate(password)) { // authenticate
            account.deposit(amount, transId);
            return true;
        } else {
            return false;
        }
    }

    public boolean withdraw(String id, String password, int amount, int transId) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if (account == null) return false;
        if (account.authenticate(password)) { // authenticate
            return account.withdraw(amount, transId);
        } else {
            return false;
        }
    }

    public boolean transfer(String sourceId, String password, String targetId, int amount, int transId) {
        //TODO: Problem 1.1
        BankAccount sourceAccount = find(sourceId);
        if (sourceAccount == null) return false;
        if (!sourceAccount.authenticate(password)) return false;

        BankAccount targetAccount = find(targetId);
        if (targetAccount == null) return false;

        if (sourceAccount.send(amount, transId)) {
            targetAccount.receive(amount, transId);
            return true;
        } else {
            return false;
        }
    }

    public Event[] getEvents(String id, String password) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if (account == null) return null;
        if (account.authenticate(password)) {
            Event[] events = account.getEvents();
            if(events[0]==null){
                return null;
            }
            int idx=0;
            for(Event event: events){
                if(event==null){
                    break;
                }
                idx++;
            }
            Event[] resizedEvents = new Event[idx];
            for(int i=0; i<idx; i++){
                resizedEvents[i] = events[i];
            }
            return resizedEvents;
        } else {
            return null;
        }
    }

    public int getBalance(String id, String password) {
        //TODO: Problem 1.1
        BankAccount account = find(id);
        if (account == null) {
            return -1;
        }
        if (account.authenticate(password)) {
            return account.getBalance();
        } else {
            return -1;
        }
    }

    private static String randomUniqueStringGen() {
        return Encryptor.randomUniqueStringGen();
    }

    private BankAccount find(String id) {
        for (int i = 0; i < numAccounts; i++) {
            if (ids[i].equals(id)) {
                //System.out.println(ids[i]);
                return accounts[i];
            }
        }
        return null;
    }


    private BankSecretKey secretKey;

    public BankPublicKey getPublicKey() {
        BankKeyPair keypair = Encryptor.publicKeyGen(); // generates two keys : BankPublicKey, BankSecretKey
        secretKey = keypair.deckey; // stores BankSecretKey internally
        return keypair.enckey;
    }

    int maxHandshakes = 10000;
    int numSymmetrickeys = 0;
    BankSymmetricKey[] bankSymmetricKeys = new BankSymmetricKey[maxHandshakes];
    String[] AppIds = new String[maxHandshakes];

    public int getAppIdIndex(String AppId) {
        for (int i = 0; i < numSymmetrickeys; i++) {
            if (AppIds[i].equals(AppId)) {
                return i;
            }
        }
        return -1;
    }

    public void fetchSymKey(Encrypted<BankSymmetricKey> encryptedKey, String AppId) {
        //TODO: Problem 1.2
        if(encryptedKey==null) return;
        if(encryptedKey.decrypt(secretKey)==null) return;
        int idx = getAppIdIndex(AppId);
        if(idx==-1){
            bankSymmetricKeys[numSymmetrickeys] = encryptedKey.decrypt(secretKey);
            AppIds[numSymmetrickeys] = AppId;
            numSymmetrickeys++;
        } else bankSymmetricKeys[idx] = encryptedKey.decrypt(secretKey);
    }

    public Encrypted<Boolean> processRequest(Encrypted<Message> messageEnc, String AppId) {
        //TODO: Problem 1.2
        int idx = getAppIdIndex(AppId);
        if(idx==-1) return null;
        if(messageEnc==null) return null;
        if(messageEnc.decrypt(bankSymmetricKeys[idx])==null) return null;
        Message message = messageEnc.decrypt(bankSymmetricKeys[idx]);
        String request = message.getRequestType();
        boolean result = false;
        if(request.equals("deposit")){
            result = deposit(message.getId(), message.getPassword(), message.getAmount(), message.getTransId());
        } else if(request.equals("withdraw")){
            result = withdraw(message.getId(), message.getPassword(), message.getAmount(), message.getTransId());
        } else if(request.equals("compensate")) {
            result = compensate(message.getId(), message.getPassword(), message.getQnA(), message.getTransIdList());
        }
        return new Encrypted<>(result, bankSymmetricKeys[idx]);
    }

    public void createAccount(String id, String password, int initBalance, String question, String answer) {
        //TODO: Problem 1.3
        BankAccount account = find(id);
        if(account!=null && account.authenticate(password)){
            if(question.equals("BestProfessor") || question.equals("BestTA")){
                if(!answer.contains(" ") && answer.matches("[a-zA-Z]+")){
                    account.setQuestion(question);
                    account.setAnswer(answer);
                }
            }
        }
        if(account==null){
            BankAccount bankAccount = new BankAccount(id, password, initBalance);
            bankAccount.setQuestion(question);
            bankAccount.setAnswer(answer);
            accounts[numAccounts] = bankAccount;
            ids[numAccounts] = id;
            numAccounts++;
        }
    }

    public boolean compensate(String id, String password, String questionAnswer, int[] transIdList) {
        //TODO: Problem 1.3
        BankAccount account = find(id);
        if(account==null) return false;
        if(account.authenticate(password) && account.secondaryAuthenticate(questionAnswer)){
            Event[] events = getEvents(id, password);
            for(int transId: transIdList){
                for(Event event: events){
                    if(event.getTransId() == transId && event instanceof WithdrawEvent) {
                        account.deposit(event.getAmount(), transId);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
