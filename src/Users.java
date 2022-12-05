import java.util.concurrent.ConcurrentHashMap;

public class Users
{
    private ConcurrentHashMap<String, String> users = new ConcurrentHashMap<>();

    public Users(ConcurrentHashMap<String, String> users)
    {
        this.users = users;
    }

    public ConcurrentHashMap<String, String> getUsers()
    {
        return this.users;
    }
}
