#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    upDataToFtp.sh
## Description: to Up Data To Ftp
## Version:     1.0
## Author:      mashencai
## Created:     2018-03-05
################################################################################
#set -x  ## 用于调试用，不用的时候可以注释掉

#---------------------------------------------------------------------#
#                              定义变量                                #
#---------------------------------------------------------------------#
cd `dirname $0`
BIN_DIR=`pwd`    ### bin目录
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf    ### 项目根目录

LIB_DIR=$DEPLOY_DIR/lib        ## Jar 包目录
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`    ## jar 包位置以及第三方依赖jar包，绝对路径
LIB_JARS=${LIB_JARS}
LOG_DIR=${DEPLOY_DIR}/logs                       ## log 日记目录
LOG_FILE=${LOG_DIR}/upDataToFtp.log        ##  log 日记文件

#####################################################################
# 函数名: upDataToFtp
# 描述: 把consumer 消费组启动起来
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function upDataToFtp()
{
    if [ ! -d $LOG_DIR ]; then
        mkdir $LOG_DIR;
    fi
    echo "up data to ftp start..."
    nohup java -server -Xms2g -Xmx4g  -XX:PermSize=512m -XX:MaxPermSize=512m  -classpath $CONF_DIR:$LIB_JARS com.hzgc.test.updata.UpDataToFtp > ${LOG_FILE} 2>&1 &
 }

#####################################################################
# 函数名: main
# 描述: 脚本主要业务入口
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function main()
{
    upDataToFtp
}

## 脚本主要业务入口
main
