/*
 *  Copyright 2010 Ryszard Wiśniewski <brut.alll@gmail.com>.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package brut.util;

import brut.common.BrutException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
public class OS {
    public static void rmdir(File dir) throws BrutException {
        rmdir(dir.getAbsolutePath());
    }

    public static void rmdir(String dir) throws BrutException {
        exec(new String[]{"rm", "-rf", dir});
    }

    public static void cpdir(File src, File dest) throws BrutException {
        cpdir(src.getAbsolutePath(), dest.getAbsolutePath());
    }

    public static void cpdir(String src, String dest) throws BrutException {
        exec(new String[]{"cp", "-r", src, dest});
    }

    public static void exec(String[] cmd) throws BrutException {
        Process ps = null;
        try {
            ps = Runtime.getRuntime().exec(cmd);
            if (ps.waitFor() != 0) {
                IOUtils.copy(ps.getInputStream(), System.out);
                IOUtils.copy(ps.getErrorStream(), System.out);
                throw new BrutException(
                    "could not exec command: " + Arrays.toString(cmd));
            }
        } catch (IOException ex) {
            throw new BrutException(
                "could not exec command: " + Arrays.toString(cmd), ex);
        } catch (InterruptedException ex) {
            throw new BrutException(
                "could not exec command: " + Arrays.toString(cmd), ex);
        }
    }

    public static File createTempDirectory() throws BrutException {
        try {
            File tmp = File.createTempFile("BRUT", null);
            if (!tmp.delete()) {
                throw new BrutException("Could not delete tmp file: " + tmp.getAbsolutePath());
            }
            if (!tmp.mkdir()) {
                throw new BrutException("Could not create tmp dir: " + tmp.getAbsolutePath());
            }
            return tmp;
        } catch (IOException ex) {
            throw new BrutException("Could not create tmp dir", ex);
        }
    }
}
