package com.edsonk.musicplayer.utils;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Serializable {

    public static void serialize(String filename, Object object, Context context){
        try{
            FileOutputStream file=context.openFileOutput(filename,Context.MODE_PRIVATE);
            ObjectOutputStream oos;
            try{
                oos=new ObjectOutputStream(file);
                oos.writeObject(object);
                oos.flush();
                oos.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static Object deSerializer(String filname,Context context){
        try{
            FileInputStream file=context.openFileInput(filname);
            ObjectInputStream ois;
            try{
                ois = new ObjectInputStream(file);
                try{
                    Object object=ois.readObject();
                    ois.close();
                    return object;
                }catch(ClassNotFoundException | IOException e){
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
