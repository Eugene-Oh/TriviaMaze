package Model;

import java.util.HashMap;

/**
 * Mazeroom is the rooms that will reside in the maze
 * that users will need to unlock inorder to proceed.
 */
public class MazeRoom {
    /**
     * Class Fields
     */
    /**
     * Location of the room
     */
    private int myLocation;

    /**
     * Shows if  user can pass to North
     */
    private Boolean canPassN;

    /**
     * Shows if  user can pass to South
     */
    private Boolean canPassS;

    /**
     * Shows if  user can pass to West
     */
    private Boolean canPassW;

    /**
     * Shows if  user can pass to East
     */
    private Boolean canPassE;

    /**
     * Shows if  user unlocked the room
     */
    private Boolean isLocked;

    /**
     * Model.MazeRoom constructor
     *
     * @param theLocation location of the room
     * @param thePassN    if user can pass to N
     * @param thePassS    if user can pass to S
     * @param thePassW    if user can pass to W
     * @param thePassE    if user can pass to E
     */
    public MazeRoom(int theLocation, Boolean thePassN, Boolean thePassS, Boolean thePassW, Boolean thePassE) {
        myLocation = theLocation;
        canPassN = thePassN;
        canPassS = thePassS;
        canPassW = thePassW;
        canPassE = thePassE;
        isLocked = true;
    }

    public HashMap<String, Boolean> getPasses() {
        HashMap<String, Boolean> hash = new HashMap<>();
        hash.put("canPassN", canPassN);
        hash.put("canPassS", canPassS);
        hash.put("canPassW", canPassW);
        hash.put("canPassE", canPassE);
        return hash;
    }

    public void setCanPasses(HashMap<String, Boolean> hash) {
        this.canPassN = hash.get("canPassN");
        this.canPassS = hash.get("canPassS");
        this.canPassW = hash.get("canPassW");
        this.canPassE = hash.get("canPassE");
    }

    public int getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(int myLocation) {
        this.myLocation = myLocation;
    }

    public Boolean getLocked() {
        return isLocked;
    }

    private void setLocked(Boolean locked) {
        isLocked = locked;
    }
}
