## README

#### 工具简介
FtpTestUtil是一个用于模拟多路摄像头向ftp发送图片的工具。  
目录./conf/ftpTest.properties可用于配置要发送图片的ftp ip地址、ftp端口号、模拟的摄像头路数和图片数。  
目录./conf/picFrom下存放要发送的一组图片，包括大图、小图和json文件。  
文件./bin/upDataToFtp.sh用于启动发送图片的客户端程序。

#### 使用方法
1、从 https://github.com/sukidasakura/FtpTestUtil 下载工具到本地，mvn clean package编译打包。  
2、打包后，将压缩包FtpTestUtil.tar.gz放到发送图片的客户端机器上。  
3、解压FtpTestUtil.tar.gz后，修改./conf/ftpTest.properties，例如：  
```
ip=172.18.18.163  
port=2121  
loopNum=2  
threadNum=3  
```
其中，ip表示要发送图片的ftp服务器的ip地址  
port表示要发送图片的ftp服务器的端口号  
loopNum表示要发送图片的组数（由于./conf/picFrom下只存放了一组图片，因此循环几次代表发送几组）  
threadNum表示模拟的发送图片的摄像头路数（需要按照所在客户端的逻辑cpu核数合理配置）

4、执行./bin/upDataToFtp.sh脚本，向Ftp服务器发送图片。
