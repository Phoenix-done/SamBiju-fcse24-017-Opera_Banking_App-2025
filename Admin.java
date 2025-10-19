import java.util.List;
public class Admin implements User {
    private long adminId;
    private List<String> permissions;

    @Override
    public void accessAccount() {
        // Admin-level access as customers and staff shouldn't have admin level access
    }

    public List<String> getPermissions() { 
        return permissions;
    }

    public void setPermissions (List<String> permissions ){
        this.permissions = permissions;
    }
}
