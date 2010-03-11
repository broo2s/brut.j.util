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

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;

/**
 * @author Ryszard Wiśniewski <brut.alll@gmail.com>
 */
abstract public class Jar {
    private final static Set<String> mLoaded = new HashSet<String>();

    public static void load(String libPath) {
        if (mLoaded.contains(libPath)) {
            return;
        }

        File libFile;
        try {
            libFile = extractToTmp(libPath);
        } catch (IOException ex) {
            throw new UnsatisfiedLinkError(ex.getMessage());
        }

        System.load(libFile.getAbsolutePath());
    }

    public static File extractToTmp(String resourcePath) throws IOException {
        return extractToTmp(resourcePath, "brut_util_Jar_");
    }

    public static File extractToTmp(String resourcePath, String tmpPrefix)
            throws IOException {
        InputStream in = Class.class.getResourceAsStream(resourcePath);
        if (in == null) {
            throw new FileNotFoundException(resourcePath);
        }

        File fileOut = File.createTempFile(tmpPrefix, null);
        fileOut.deleteOnExit();

        OutputStream out = new FileOutputStream(fileOut);
        IOUtils.copy(in, out);

        in.close();
        out.close();

        return fileOut;
    }
}
