package Lab10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankSimulation3 {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        
        // 2 thread-тэй сан (pool) үүсгэх. 
        // Энэ нь 3 ажлыг 2 thread хувааж хийнэ гэсэн үг.
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Даалгавруудыг (Runnable) executor руу илгээх
        executor.submit(new Customer(account, "deposit", 500));
        executor.submit(new Customer(account, "withdraw", 700));
        executor.submit(new Customer(account, "withdraw", 600));

        // Шинэ ажил авахыг зогсоож, одоо байгаа ажлуудыг дуусгахыг тушаах
        executor.shutdown();

        // Бүх ажил дуустал хүлээх (join-той төстэй үйлдэл)
        // 1 минут хүртэл хүлээгээд амжихгүй бол дараагийн мөр рүү шилжинэ
        if (executor.awaitTermination(1, TimeUnit.MINUTES)) {
            System.out.println("-------------------------");
            System.out.println("Final Balance: " + account.getBalance());
        }
    }
}