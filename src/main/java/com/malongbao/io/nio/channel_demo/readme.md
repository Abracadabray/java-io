* Description:
*      1、write() : 写数据到一个文件
*      2、read() : 从文件读数据到控制台
*      3、copyFile（）：将数据从一个文件写入到另一个文件
*      4、Channle的分散 (Scatter) 和聚集 (Gather)
*              分散读取（Scatter ）:是指把Channel通道的数据读入到多个缓冲区中去
*              聚集写入（Gathering ）是指将多个 Buffer 中的数据“聚集”到 Channel。
*      5、transferFrom()：从目标通道中去复制原通道数据
*      6、transferTo()：把原通道数据复制到目标通道