/**
 * Copyright [2014] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.orm.converter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.charset.Charset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;

import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.netbeans.editor.GuardedDocument;
import org.netbeans.jpa.modeler.collaborate.issues.ExceptionUtils;
import org.netbeans.lib.editor.util.swing.PositionRegion;
import org.netbeans.modules.editor.indent.api.Reformat;
import org.netbeans.orm.converter.compiler.InvalidDataException;
import org.netbeans.orm.converter.compiler.WritableSnippet;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.text.NbDocument;
import org.openide.util.UserQuestionException;

public class ORMConverterUtil {

    public static final String AT = "@";
    public static final String CLASS_SUFFIX = ".class";
    public static final String CLOSE_BRACES = "}";
    public static final String CLOSE_PARANTHESES = ")";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String DOT = ".";
    public static final String GREATER_THAN = ">";
    public static final String IMPORT = "import ";
    public static final String LESS_THAN = "<";
    public static final String NEW_LINE = "\n";
    public static final String TAB = "    ";
    public static final String OPEN_BRACES = "{";
    public static final String OPEN_PARANTHESES = "(";
    public static final String QUESTION = "?";
    public static final String SINGLE_QUOTE = "'";
    public static final String QUOTE = "\"";
    public static final String SEMICOLON = ";";
    public static final String SOURCE_SUFFIX = ".java";
    public static final String SPACE = " ";
    public static final String UNDERSCORE = "_";
    public static final String EQUAL = "=";

    public static File createFile(String parentDir, String childDir,
            String fileName) throws IOException {

        if (childDir == null) {
            return createFile(parentDir, fileName);
        }

        File dir = new File(parentDir, childDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public static File createFile(String parentDir, String fileName)
            throws IOException {

        File dir = new File(parentDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    public static Collection<String> eliminateSamePkgImports(
            String classPackage, Collection<String> importSnippets) {

        List<String> uniqueImports = new ArrayList<String>();

        for (String importSnippet : importSnippets) {

            ClassHelper importSnippetHelper = new ClassHelper(importSnippet);

            if (importSnippetHelper.getPackageName() == null) {
                continue;
            }

            if (!importSnippetHelper.getPackageName().equals(classPackage)) {
                uniqueImports.add(importSnippet);
            }
        }

        return uniqueImports;
    }

    public static byte[] getBytes(File file) throws IOException {

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        if (file.length() > Integer.MAX_VALUE) {
            throw new IOException("file is too large for single read");
        }

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;

        try {

            fileInputStream = new FileInputStream(file);
            int read = fileInputStream.read(bytes);

            if (read != file.length()) {
                throw new IOException("could not read entire file");
            }

        } finally {
            fileInputStream.close();
        }

        return bytes;
    }

    public static String getCommaSeparatedString(Collection<String> values) {

        if (values == null || values.size() == 0) {
            return null;
        }

        StringBuilder builder = new StringBuilder();

        for (String value : values) {
            builder.append(value);
            builder.append(COMMA);
        }

        return builder.substring(0, builder.length() - 1);
    }

    public static String getContent(File file) throws IOException {
        return new String(getBytes(file));
    }

    public static String getContent(File file, final String charsetName)
            throws IOException {

        return new String(getBytes(file), charsetName);
    }

    public static Template getTemplate(String templateName) throws Exception {

        ClassLoader classLoader = ORMConverterUtil.class.getClassLoader();

        InputStream inputStream = classLoader.getResourceAsStream(
                "velocity.properties");

        Properties properties = new Properties();
        properties.load(inputStream);

        Velocity.init(properties);

        Template template = Velocity.getTemplate(templateName);

        return template;
    }

    public static Collection<String> processedImportStatements(
            Collection<String> importSnippets) {

        Collection<String> processedStatements = new ArrayList<String>();

        for (String element : importSnippets) {
            processedStatements.add(IMPORT + element + SEMICOLON);
        }

        return processedStatements;
    }

    public static void writeContent(String content, File file)
            throws IOException {

        writeContent(content, Charset.defaultCharset().toString(), file);
    }

    public static void writeContent(String content, String charset, File file)
            throws IOException {

        if (file.isDirectory()) {
            throw new IOException("Cannot write content to directory");
        }

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fos = new FileOutputStream(file);
        fos.write(content.getBytes(charset));
        fos.close();
    }

    public static FileObject writeSnippet(WritableSnippet writableSnippet, File destDir)
            throws InvalidDataException, IOException {

        String content = writableSnippet.getSnippet();

        File sourceFile = ORMConverterUtil.createFile(
                destDir.getAbsolutePath(),
                writableSnippet.getClassHelper().getSourcePath(),
                writableSnippet.getClassHelper().getClassNameWithSourceSuffix());

        ORMConverterUtil.writeContent(content, sourceFile);
        final FileObject fo = FileUtil.toFileObject(sourceFile);
        formatFile(fo);
        return fo;
    }

    public static void formatFile(FileObject fo) {
        try {
            DataObject dobj = DataObject.find(fo);
            EditorCookie ec = dobj.getLookup().lookup(EditorCookie.class);
            ec.close();
            StyledDocument sd;
            try {
                sd = ec.openDocument();
            } catch (UserQuestionException uqe) {
                uqe.confirmed();
                sd = ec.openDocument();
            }
            final StyledDocument doc = sd;
            final Reformat reformat = Reformat.get(doc);

            reformat.lock();

            try {
                NbDocument.runAtomic(doc, () -> {
                    try {
                        reformat(reformat, doc, 0, doc.getLength(), new AtomicBoolean());
                    } catch (BadLocationException ex) {
                        ExceptionUtils.printStackTrace(ex);
                    }
                });
            } finally {
                reformat.unlock();
            }

            ec.saveDocument();

        } catch (IOException ex) {
            ExceptionUtils.printStackTrace(ex);
        }
    }

    //TODO: copied from org.netbeans.editor.ActionFactory:
    static void reformat(Reformat formatter, Document doc, int startPos, int endPos, AtomicBoolean canceled) throws BadLocationException {
        final GuardedDocument gdoc = (doc instanceof GuardedDocument)
                ? (GuardedDocument) doc : null;

        int pos = startPos;
        if (gdoc != null) {
            pos = gdoc.getGuardedBlockChain().adjustToBlockEnd(pos);
        }

        LinkedList<PositionRegion> regions = new LinkedList<PositionRegion>();
        while (pos < endPos) {
            int stopPos = endPos;
            if (gdoc != null) { // adjust to start of the next guarded block
                stopPos = gdoc.getGuardedBlockChain().adjustToNextBlockStart(pos);
                if (stopPos == -1 || stopPos > endPos) {
                    stopPos = endPos;
                }
            }

            if (pos < stopPos) {
                regions.addFirst(new PositionRegion(doc, pos, stopPos));
                pos = stopPos;
            } else {
                pos++; //ensure to make progress
            }

            if (gdoc != null) { // adjust to end of current block
                pos = gdoc.getGuardedBlockChain().adjustToBlockEnd(pos);
            }
        }

        if (canceled.get()) {
            return;
        }
        // Once we start formatting, the task can't be canceled

        for (PositionRegion region : regions) {
            formatter.reformat(region.getStartOffset(), region.getEndOffset());
        }
    }

}
