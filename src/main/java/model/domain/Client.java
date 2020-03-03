package model.domain;

import java.util.Objects;

public class Client extends BaseEntity<Long> {
    private String clientNumber;
    private String firstName;
    private String lastName;
    private int age;

    public Client() {
        setId(0L);
        this.clientNumber = "";
        this.firstName = "";
        this.lastName = "";
        this.age = 0;
    }

    public Client(Long ID,String clientNumber, String fName, String lName , int age) {
        super.setId(ID);
        this.clientNumber = clientNumber;
        this.firstName = fName;
        this.lastName = lName;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return super.getId().equals(client.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId());
    }

    @Override
    public String toString() {
        return "Client Number: '" + clientNumber + '\'' +
                ", First Name: '" + firstName + '\'' +
                ", Last Name: '" + lastName + '\'' +
                ", Age: " + age +
                ", ID: '" + getId() + '\'';
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
