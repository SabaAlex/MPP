package model.domain;

import java.util.Objects;

public class Client extends BaseEntity<Long> {
    private String clientNumber;
    private String fName;
    private String lName;
    private int age;

    private static long ids = 1;

    public Client() {
        long id;
        id = ids;
        ids++;
        setId(id);
        this.clientNumber = "";
        this.fName = "";
        this.lName = "";
        this.age = 0;
    }

    public Client(String clientNumber, String fName, String lName , int age) {
        long id;
        id = ids;
        ids++;
        setId(id);
        this.clientNumber = clientNumber;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getClientNumber().equals(client.getClientNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClientNumber());
    }

    @Override
    public String toString() {
        return "Client Id: '" + clientNumber + '\'' +
                ", First Name: '" + fName + '\'' +
                ", Last Name: '" + lName + '\'' +
                ", Age: " + age +
                ", Id: '" + getId() + '\'';
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
