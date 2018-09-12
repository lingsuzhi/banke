Attribute VB_Name = "Module1"
  Public Declare Function GetTempPath Lib "kernel32" Alias _
        "GetTempPathA" (ByVal nBufferLength As Long, ByVal lpBuffer As String) As Long

    Public Const MAX_PATH = 260

    Public Sub CompactJetDatabase(Location As String, Optional BackupOriginal As Boolean = True)

  
    Dim strBackupFile As String

    Dim strTempFile As String

    '检查数据库文件是否存在

    If Len(Dir(Location)) = 0 Then
        MsgBox Location + " 文件不存在"
        Exit Sub
    End If

     ' 如果需要备份就执行备份

     If BackupOriginal = True Then

     strBackupFile = Location + Format$(Now, "-yyyy-MM-dd--HH--mm--ss") ' GetTemporaryPath & "backup.mdb"

     If Len(Dir(strBackupFile)) Then Kill strBackupFile

     FileCopy Location, strBackupFile

     End If

     ' 创建临时文件名

     strTempFile = GetTemporaryPath & "temp.mdb"

     If Len(Dir(strTempFile)) Then Kill strTempFile

     '通过DBEngine 压缩数据库文件

     DBEngine.CompactDatabase Location, strTempFile, , , ";Pwd=554600654"
 'Dao.CompactDataBase(OldDB,NewDB,,,';Pwd=123456'); //压缩和修复数据库,并修改密码
     ' 删除原来的数据库文件

     Kill Location

     ' 拷贝刚刚压缩过临时数据库文件至原来位置

     FileCopy strTempFile, Location

   

     ' 删除临时文件

     Kill strTempFile


CompactErr:

            Exit Sub

    End Sub

    Public Function GetTemporaryPath()

    Dim strFolder As String

    Dim lngResult As Long

    strFolder = String(MAX_PATH, 0)

    lngResult = GetTempPath(MAX_PATH, strFolder)

    If lngResult <> 0 Then

     GetTemporaryPath = Left(strFolder, InStr(strFolder, Chr(0)) - 1)

    Else

     GetTemporaryPath = ""

    End If

    End Function

