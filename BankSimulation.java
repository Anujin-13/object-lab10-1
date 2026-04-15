package Lab10;

class BankAccount {
    private int balance = 1000;

    // Синхрончлолгүй тул уралдааны нөхцөл (race condition) үүснэ
    public void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited " + amount + ", Balance: " + balance);
    }

    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn " + amount + ", Balance: " + balance);
        } else {
            System.out.println("Insufficient funds for " + amount);
        }
    }

    public int getBalance() {
        return balance;
    }
}

class Customer implements Runnable {
    private BankAccount account;
    private String action;
    private int amount;

    public Customer(BankAccount account, String action, int amount) {
        this.account = account;
        this.action = action;
        this.amount = amount;
    }

    @Override
    public void run() {
        // Даалгаврын дагуу action-аас хамаарч үйлдлийг дуудах
        if (action.equals("deposit")) {
            account.deposit(amount);
        } else if (action.equals("withdraw")) {
            account.withdraw(amount);
        }
    }
}

public class BankSimulation {
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        
        // 3 хэрэглэгч (Thread) үүсгэх
        Thread[] customers = new Thread[3];
        customers[0] = new Thread(new Customer(account, "deposit", 500));
        customers[1] = new Thread(new Customer(account, "withdraw", 700));
        customers[2] = new Thread(new Customer(account, "withdraw", 600));

        // Thread-үүдийг ажиллуулж эхлэх
        for (Thread t : customers) {
            t.start();
        }

        // Санамж: Энд join() байхгүй тул Final Balance нь бүх гүйлгээ 
        // дуусахаас өмнө хэвлэгдэж магадгүйг анхаарна уу.
    }
}
