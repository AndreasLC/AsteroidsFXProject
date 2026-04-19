package dk.sdu.cbse.common.data;

public class GameKeys {
    private static boolean[] keys; // Newly pressed keys
    private static boolean[]pkeys; // Previous pressed keys.

    private static final int Num_Keys = 5; // Total of 5 keys to press

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT =3;
    public static final int SPACE = 4;


    public GameKeys() // Contructer inizialing the two arrays with the given size of 4. All set to false, because this is default.
    {
        keys = new boolean[Num_Keys];
        pkeys = new boolean[Num_Keys];
    }
    public void update() // update previous keys with newly pressed keys.
    {
        for (int i = 0; i<Num_Keys; i++){
            pkeys[i]=keys[i];
        }
    }

    public void setKey(int k , boolean b) // Update keys[] to true when key is pressed.
    {
        keys[k] = b;
    }

    public boolean keyIsDown (int k) // Returns true if key is pressed.
    {
        return keys[k];
    }

    public boolean keyIsPressed (int k) // Returns true if key is pressed in a single frame and not previously. Single press.
    {
        return keys[k] && !pkeys[k];
    }

}
