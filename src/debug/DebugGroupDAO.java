package debug;

import model.Group;
import repository.GroupDAO;

public class DebugGroupDAO {
    public static void main(String[] args) {
        Group group = new Group();
        group.setName("test");
        GroupDAO.addGroup(group);
    }
}
