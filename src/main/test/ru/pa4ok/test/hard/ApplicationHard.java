package ru.pa4ok.test.hard;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * по итогу я добил идею одновременнной итерации 2х коллекций
 * смотря на это и на мое первое решение в 3 строки на стримах
 * хочется только пойти и построить алу в майнкрафте
 */
public class ApplicationHard
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

        orders.sort(Comparator.comparingInt(o -> o.time));
        requests.sort(Comparator.comparingInt(r -> r.time));

        TreeSet<Request> closedRequests = new TreeSet<>(Comparator.comparingInt(value -> value.id));
        HashMap<Integer, Request> openRequests = new HashMap<>();

        int requestIndex = 0;
        int requestStartIndex = 0;

        for(int i=0; i<orders.size(); i++)
        {
            Order order = orders.get(i);

            Request r;
            while(requestIndex < requests.size() && (r = requests.get(requestIndex)).time <= order.time) {
                if(r.start) {
                    openRequests.put(r.id, r);
                }
                requestIndex++;
            }

            for(Request openRequest : openRequests.values()) {
                if(order.start && openRequest.requestType == 1) {
                    openRequest.result += order.cost;
                } else if(!order.start && openRequest.requestType == 2 && order.time <= openRequest.time + openRequest.duration) {
                    openRequest.result += order.duration;
                }
            }

            if(i != orders.size()-1 && order.time != orders.get(i+1).time) {
                while (requestStartIndex < requests.size() && (r = requests.get(requestStartIndex)).time <= order.time) {
                    if (!r.start) {
                        r = openRequests.remove(r.id);
                        closedRequests.add(r);
                    }
                    requestStartIndex++;
                }
                requestIndex = requestStartIndex;
            }
        }

        StringBuilder sb = new StringBuilder();
        closedRequests.addAll(openRequests.values());
        for(Request r : closedRequests) {
            sb.append(r.result).append(" ");
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
            int startTime = Integer.parseInt(arr[0]);
            int endTime = Integer.parseInt(arr[1]);
            int duration = endTime - startTime;
            int cost = Integer.parseInt(arr[2]);

            list.add(new Order(true, startTime, duration, cost));
            list.add(new Order(false, endTime, duration, cost));
        }

        return list;
    }

    private static List<Request> readRequests(BufferedReader reader) throws IOException
    {
        int count = Integer.parseInt(reader.readLine());
        List<Request> list = new ArrayList<>();

        for(int i=0; i<count; i++) {
            String[] arr = reader.readLine().split(QUOTE_PATTERN);
            int startTime = Integer.parseInt(arr[0]);
            int endTime = Integer.parseInt(arr[1]);
            int duration = endTime - startTime;
            int requestType = Integer.parseInt(arr[2]);

            list.add(new Request(true, startTime, duration, i, requestType));
            list.add(new Request(false, endTime, duration, i, requestType));
        }

        return list;
    }
}

class Order
{
    public final boolean start;
    public final int time;
    public final int duration;
    public final int cost;

    public Order(boolean start, int time, int duration, int cost) {
        this.start = start;
        this.time = time;
        this.duration = duration;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "start=" + start +
                ", time=" + time +
                ", duration=" + duration +
                ", cost=" + cost +
                '}';
    }
}

class Request
{
    public final boolean start;
    public final int time;
    public final int duration;
    public final int id;
    public final int requestType;

    public int result;

    public Request(boolean start, int time, int duration, int id, int requestType) {
        this.start = start;
        this.time = time;
        this.duration = duration;
        this.id = id;
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "start=" + start +
                ", time=" + time +
                ", duration=" + duration +
                ", id=" + id +
                ", requestType=" + requestType +
                ", result=" + result +
                '}';
    }
}