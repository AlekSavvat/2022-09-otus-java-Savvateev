package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.AbstractMap;
public class CustomerService {

    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    TreeMap<Customer, String> treeMap = new TreeMap<>(Comparator.comparingLong(s -> s.getScores()));
    public Map.Entry<Customer, String> getSmallest() {
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> entry = treeMap.firstEntry();
        return entry == null ? null : new AbstractMap.SimpleEntry<>(
                Customer.copy(entry.getKey()) , entry.getValue() );
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = treeMap.higherEntry(customer);
        return entry == null ? null : new AbstractMap.SimpleEntry<>(
                Customer.copy(entry.getKey()) , entry.getValue()
        );
    }

    public void add(Customer customer, String data) {
        treeMap.put(customer, data);
    }
}
