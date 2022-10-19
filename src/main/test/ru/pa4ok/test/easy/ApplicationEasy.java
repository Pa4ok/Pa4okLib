package ru.pa4ok.test.easy;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * решение без стримов основанное на том что можно отсортировать коллеции
 * и прервать итерации когда условия перестанут совпадать
 * можно дополнительно добавить еще 1 коллекцию и выходить из обоих видов циклов
 * но для этого придется копировать и сортировать на еще 1 колекцию больше
 * можно еще кастомным бинарным поиском определять начало для вложенных циклов
 * но уже 6 утра и чет хочется спать
 */
public class ApplicationEasy
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

        orders.sort(Comparator.comparingInt(o -> o.startTime));
        requests.sort(Comparator.comparingInt(r -> r.startTime));

        //List<Order> ordersByEnd = new ArrayList<>(orders);
        //ordersByEnd.sort(Comparator.comparingInt(o -> o.endTime));

        TreeSet<RequestResult> resultSet = new TreeSet<>(Comparator.comparingInt(r -> r.id));
        for(Request r : requests)
        {
            int counter = 0;
            if(r.type == 1) {
                for(Order o : orders) {
                    if(o.startTime >= r.startTime && o.startTime <= r.endTime) {
                        counter += o.cost;
                    } else if(o.startTime > r.endTime) {
                        break;
                    }
                }
            } else {
                for(Order o : orders/*ByEnd*/) {
                    if(o.endTime >= r.startTime && o.endTime <= r.endTime) {
                        counter += (o.endTime - o.startTime);
                    } /*else if(o.endTime > r.endTime) {
                        break;
                    }*/
                }
            }
            resultSet.add(new RequestResult(r.id, counter));
        }

        StringBuilder sb = new StringBuilder();
        for(RequestResult result : resultSet) {
            sb.append(result.value).append(" ");
        }

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
            list.add(new Request(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), i));
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
    public final int id;

    public Request(int startTime, int endTime, int type, int id) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Request{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", type=" + type +
                ", id=" + id +
                '}';
    }
}

class RequestResult
{
    public final int id;
    public final int value;

    public RequestResult(int id, int value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}