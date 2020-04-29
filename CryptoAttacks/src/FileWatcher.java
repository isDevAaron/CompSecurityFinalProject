/*
 * This class notifies when a file is changed
 * This has been implemented in creation of alice, bob, 
 * and chuck's user interfaces
 */

import java.util.*;
import java.io.*;

public abstract class FileWatcher extends TimerTask {
  private long timeStamp;
  private File file;

  public FileWatcher( File file ) {
    this.file = file;
    this.timeStamp = file.lastModified();
  }

  // main function that checks for the file change
  public final void run() {
    long timeStamp = file.lastModified();

    if( this.timeStamp != timeStamp ) {
      this.timeStamp = timeStamp;
      onChange(file);
    }
  }

  protected abstract void onChange( File file );
}