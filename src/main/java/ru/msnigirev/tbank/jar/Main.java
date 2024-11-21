package ru.msnigirev.tbank.jar;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class Main {
    public static void main(String[] mainArgs) throws InterruptedException {

        Args args = new Args();
        int count = 1;
        try {
            JCommander.newBuilder()
                    .addObject(args)
                    .build()
                    .parse(mainArgs);
            if(args.mode.equals("multi-thread"))
                count = Integer.parseInt(args.count);

            ThreadPoolExecutor threadPool = new ThreadPoolExecutor(count);

            for (String image : args.files) threadPool.submit(new ImageDownloader(args.folder, image));
            do {
                Thread.sleep(100);
            } while (args.files.size() != threadPool.getCompletedTasksCount());
            System.out.println("Процесс завершен");
            threadPool.shutdown();
        } catch (ParameterException e) {
            System.out.printf("Введено неверное имя параметра\n" + e.getMessage() +"\n");
        }
    }
}
