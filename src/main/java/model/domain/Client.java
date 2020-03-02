package model.domain;

import java.util.Objects;

public class Client extends BaseEntity<Long> {
    String clientNumber;
    String fName;
    String lName;
    int age;

    public Client() {
        this.clientNumber = "";
        this.fName = "";
        this.lName = "";
        this.age = 0;
    }

    public Client(String clientNumber, String fName, String lName , int age) {
        long a = 1;
        setId(a);
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
                ", Age: " + age;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientId) {
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
