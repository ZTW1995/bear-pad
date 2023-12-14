package com.dafeixiong.bearpad.handler;

import com.dafeixiong.bearpad.GlobalValue;
import com.dafeixiong.bearpad.exception.BearPadException;
import com.dafeixiong.bearpad.menu.SecurityMenu;
import com.dafeixiong.bearpad.util.AesMode;
import com.dafeixiong.bearpad.util.AesUtil;
import com.dafeixiong.bearpad.util.FileUtil;
import com.dafeixiong.bearpad.util.StringUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * <p>项目名称：bear-pad</p>
 * <p>类名：com.dafeixiong.bearpad.handler.FileHandler</p>
 * <p>创建时间: 2023-08-18 9:55 </p>
 * <p>修改时间: 2023-08-18 9:55 </p>
 * 文件处理器
 *
 * @author zhang.taowei
 * @version 1.0.0
 */
public class FileHandler {

    public FileHandler() {

    }

    /**
     * 打开指定文件
     * @param file
     */
    public static void openFile(File file) throws BearPadException {
        try(FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr)) {
            String line;
            StringBuilder content = new StringBuilder();
            if (".bear".equals(FileUtil.getFileType(file))) {
                if (StringUtil.isEmpty(GlobalValue.securityKey) || StringUtil.isEmpty(GlobalValue.iv)) {
                    SecurityMenu.requestSecurityKey();
                }
                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }
                // 是加密文件，需要加密处理
                content = new StringBuilder(AesUtil.decode(content.toString(), GlobalValue.securityKey, GlobalValue.iv, AesMode.AES_CBC_PKCS5Padding));
            } else {
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            GlobalValue.textArea.setText(content.toString());
        } catch (IOException e) {
            throw new BearPadException("文件读取失败！", e);
        } catch (SecurityException e) {
            throw new BearPadException("文件解密失败！", e);
        }
    }

    /**
     * 保存内容到指定文件中
     * @param file
     */
    public static void saveFile(File file) throws BearPadException {
        String content = GlobalValue.textArea.getText();
        try(FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter writer = new BufferedWriter(osw)) {
            if (".bear".equals(FileUtil.getFileType(file))) {
                if (StringUtil.isEmpty(GlobalValue.securityKey) || StringUtil.isEmpty(GlobalValue.iv)) {
                    SecurityMenu.requestSecurityKey();
                }
                // 是加密文件，需要加密处理
                content = AesUtil.encode(content, GlobalValue.securityKey, GlobalValue.iv, AesMode.AES_CBC_PKCS5Padding);
            }
            writer.write(content);
        } catch (IOException e) {
            throw new BearPadException("文件保存失败！", e);
        } catch (SecurityException e) {
            throw new BearPadException("文件加密失败！", e);
        }
    }
}
