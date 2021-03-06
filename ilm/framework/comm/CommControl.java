/*
 * iLM - interactive Learning Modules in the Internet
 * 
 * @see ./ilm/framework/comm/IlmDesktopFileRW.java
 * 
 * LInE
 * Free Software for Better Education (FSBE)
 * http://www.matematica.br
 * http://line.ime.usp.br
 * 
 */

package ilm.framework.comm;

import java.io.IOException;
import java.util.Vector;
import java.util.zip.ZipFile;
import ilm.framework.config.SystemConfig;

public class CommControl implements ICommunication {
  private SystemConfig _config;
  private IEncrypter _encrypter;
  private ICommunication _fileRW;

  public CommControl (SystemConfig config) {
    _config = config;
    _encrypter = new IlmEncrypter();
    if (_config.isApplet()) {
      _fileRW = new IlmAppletFileRW();
      }
    else {
      _fileRW = new IlmDesktopFileRW();
      }
    }


  // Called by: ilm.framework.assignment.AssignmentControl.loadMetadataFile(AssignmentControl.java:154)
  // Starting point: ilm.framework.gui.IlmBaseGUI.openAssignmentFile(IlmBaseGUI.java:291)
  public String readMetadataFile (String packageName) {
    try {
      return _fileRW.readMetadataFile(packageName);
    } catch (IOException e) {
      System.err.println("./ilm/framework/comm/CommControl.java: readMetadataFile(...): error " + e.toString());
      e.printStackTrace();
      }
    return null;
    }

  public Vector readResourceFiles (String packageName, Vector resourceList) {
    try {
      return _fileRW.readResourceFiles(packageName, resourceList);
    } catch (IOException e) {
      System.err.println("./ilm/framework/comm/CommControl.java: readResourceFiles(...): error " + e.toString());
      e.printStackTrace();
      }
    return null;
    }

  public Vector readAssignmentFiles (String packageName, Vector assignmentList) {
    try {
      return _encrypter.decryptFromFile(_fileRW.readAssignmentFiles(packageName, assignmentList));
    } catch (IOException e) {
      System.err.println("./ilm/framework/comm/CommControl.java: readAssignmentFiles(...): error " + e.toString());
      e.printStackTrace();
      }
    return null;
    }

  public ZipFile writeAssignmentPackage (String packageName, String metadata, Vector resourceNameList, Vector resourceList,
    Vector assignmentNameList, Vector assignmentList) {
    try {
      return _fileRW.writeAssignmentPackage(packageName, metadata, resourceNameList, resourceList, assignmentNameList, _encrypter.encryptFileContent(assignmentList));
    } catch (IOException e) {
      System.err.println("./ilm/framework/comm/CommControl.java: writeAssignmentPackage(...): error " + e.toString());
      e.printStackTrace();
      }
    return null;
    }

  }
