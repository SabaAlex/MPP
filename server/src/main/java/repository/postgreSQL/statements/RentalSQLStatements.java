package repository.postgreSQL.statements;

public enum RentalSQLStatements {
    INSERT("insert into ", " (id,clientid,movieid,day,month,year) values(?,?,?,?,?,?)"),
    DELETE("delete from ", " where id = ?");

    private final String cmdStart;
    private final String cmdEnd;

    public String composeMessage(String entity){
        return cmdStart + entity + cmdEnd;
    }

    RentalSQLStatements(String cmdStart, String cmdEnd) {
        this.cmdStart = cmdStart;
        this.cmdEnd = cmdEnd;
    }
}
