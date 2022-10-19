package ru.pa4ok.test.bad;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * простое и красивое решение на стримах
 * задание не зачтено: TL
 * O(n^2) еще и на стримах, грустная история...
 * Runtime.getRuntime().availableProcessors() на платформе вернул 2
 * поэтому полявился лайв хак заменить stream() на parallelStream()
 * задание не зачтено: TL
 * 2 убитых часа на так и не доделанное во время решение (см. ../hard/ApplicationHard)
 * через пару часов уже со свежей головой вернулся
 * доделал ApplicationHard и ApplicationEasy как альтернативу
 * сложно тестить на раскопированных данных, а пилить скрипт для новых лень
 */
public class ApplicationTimeOut
{
    public static final String QUOTE_PATTERN = Pattern.quote(" ");

    public static void main(String[] args) throws IOException
    {
        List<Order> orders;
        List<Request> requests;
        try(BufferedReader reader = Files.newBufferedReader(Paths.get("./input.txt"))) {
            orders = readOrders(reader);
            requests = readRequests(reader);
        }

        long startMills = System.currentTimeMillis();

        StringBuilder sb = new StringBuilder();
        requests.forEach(r -> {
            AtomicInteger counter = new AtomicInteger();
            if(r.type == 1) {
                orders.stream()
                        .filter(o -> o.startTime >= r.startTime && o.startTime <= r.endTime)
                        .forEach(o -> counter.addAndGet(o.cost));
            } else if(r.type == 2) {
                orders.stream()
                        .filter(o -> o.endTime >= r.startTime && o.endTime <= r.endTime)
                        .forEach(o -> counter.addAndGet(o.endTime - o.startTime));
            }
            sb.append(counter).append(" ");
        });

        System.out.println(System.currentTimeMillis() - startMills + "ms");

        Files.write(Paths.get("./output.txt"), sb.substring(0, sb.length()-1).getBytes());
    }

    private static List<Order> readOrders(BufferedReader reader) throws IOException
    {
        int count = Integer.parseInt(reader.readLine());
        List<Order> list = new ArrayList<>();

        for(int i=0; i<count; i++) {
            String[] arr = reader.readLine().split(QUOTE_PATTERN);
            list.add(new Order(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
        }

        return list;
    }

    private static List<Request> readRequests(BufferedReader reader) throws IOException
    {
        int count = Integer.parseInt(reader.readLine());
        List<Request> list = new ArrayList<>();

        for(int i=0; i<count; i++) {
            String[] arr = reader.readLine().split(QUOTE_PATTERN);
            list.add(new Request(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2])));
        }

        return list;
    }
}

class Order
{
    public final int startTime;
    public final int endTime;
    public final int cost;

    public Order(int startTime, int endTime, int cost) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", cost=" + cost +
                '}';
    }
}

class Request
{
    public final int startTime;
    public final int endTime;
    public final int type;

    public Request(int startTime, int endTime, int type) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Request{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                '}';
    }
}