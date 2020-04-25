package repository;


import model.exceptions.MyException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Sort {

    private ArrayList<AbstractMap.SimpleEntry<Direction,String>> sortOrder;
    private String className=null;



    public Sort(String... sortByFields)
    {
        sortOrder=new ArrayList<>();
        if(sortByFields.length==0)
        {
            throw new MyException("No fields for sorting given");
        }
        for(String field:sortByFields)
        {
            sortOrder.add(new AbstractMap.SimpleEntry<>(Direction.ASC,field));
        }

    }

    public Sort(Direction direction,String... sortByFields)
    {
        sortOrder=new ArrayList<>();
        if(sortByFields.length==0)
        {
            throw new MyException("No fields for sorting given");
        }
        for(String field:sortByFields)
        {
            sortOrder.add(new AbstractMap.SimpleEntry<>(direction,field));
        }
    }


    public void setClassName(String ClassName)
    {
        this.className=ClassName;
    }

    public <T> Iterable<T> sort(Iterable<T> toSort)
    {
        if(className == null) throw new IllegalStateException("No class specified!");
        return StreamSupport.stream(toSort.spliterator(), false)
                .sorted(new SortComparator())
                .collect(Collectors.toList());
    }

    public Sort and(Sort otherSortFields)
    {
        this.sortOrder.addAll(otherSortFields.GetSortOrder());
        return this;
    }

    public ArrayList<AbstractMap.SimpleEntry<Direction,String>> GetSortOrder()
    {
        return this.sortOrder;
    }

    private Object getValueByFieldName(Object objectToInvokeOn, String fieldName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        String fullClassName;
        if(fieldName.equals("Id")){
            fullClassName = AddPackage("BaseEntity");
        } else {
            fullClassName = AddPackage(className);
        }
        Class<?> c = Class.forName(fullClassName);
        Method method = c.getDeclaredMethod(AddGet(fieldName));
        return method.invoke(objectToInvokeOn);
    }

    private String AddGet(String fieldName) {
        return "get"+fieldName;
    }

    private String AddPackage(String className) {
        return "model.domain."+className;
    }


    private class SortComparator implements Comparator<Object>
    {
        public int compare(Object first, Object second) {
            return sortOrder.stream()
                    .map(entry -> compareObjectsByCriteria(first, second, entry))
                    .filter(value -> value != 0)
                    .findFirst()
                    .orElse(0);

        }

        private int compareObjectsByCriteria(Object first, Object second, AbstractMap.SimpleEntry<Direction, String> entry) {
            int compareResult=0;
            try {
                Object firstValue = getValueByFieldName(first, entry.getValue());
                Object secondValue = getValueByFieldName(second, entry.getValue());
                compareResult = ((Comparable)firstValue).compareTo(secondValue);
                if (entry.getKey() == Direction.DESC) {
                    compareResult *= -1; // reverse result
                }

            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException e) {
                throw new MyException("No such Class or Field !");
            }
            return compareResult;
        }
    }

    public enum Direction {
        ASC, DESC
    }
}
