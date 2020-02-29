package model.domain;

public class Client {
    String clientId;
    String fName;
    String lName;
    int age;

    public Client(String clientId,String fName, String lName , int age) {
        this.clientId = clientId;
        this.fName = fName;
        this.lName = lName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client Id: '" + clientId + '\'' +
                ", First Name: '" + fName + '\'' +
                ", Last Name: '" + lName + '\'' +
                ", Age: " + age;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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
