Attribute VB_Name = "Module1"
  Public Declare Function GetTempPath Lib "kernel32" Alias _
        "GetTempPathA" (ByVal nBufferLength As Long, ByVal lpBuffer As String) As Long

    Public Const MAX_PATH = 260

    Public Sub CompactJetDatabase(Location As String, Optional BackupOriginal As Boolean = True)

  
    Dim strBackupFile As String

    Dim strTempFile As String

    '������ݿ��ļ��Ƿ����

    If Len(Dir(Location)) = 0 Then
        MsgBox Location + " �ļ�������"
        Exit Sub
    End If

     ' �����Ҫ���ݾ�ִ�б���

     If BackupOriginal = True Then

     strBackupFile = Location + Format$(Now, "-yyyy-MM-dd--HH--mm--ss") ' GetTemporaryPath & "backup.mdb"

     If Len(Dir(strBackupFile)) Then Kill strBackupFile

     FileCopy Location, strBackupFile

     End If

     ' ������ʱ�ļ���

     strTempFile = GetTemporaryPath & "temp.mdb"

     If Len(Dir(strTempFile)) Then Kill strTempFile

     'ͨ��DBEngine ѹ�����ݿ��ļ�

     DBEngine.CompactDatabase Location, strTempFile, , , ";Pwd=554600654"
 'Dao.CompactDataBase(OldDB,NewDB,,,';Pwd=123456'); //ѹ�����޸����ݿ�,���޸�����
     ' ɾ��ԭ�������ݿ��ļ�

     Kill Location

     ' �����ո�ѹ������ʱ���ݿ��ļ���ԭ��λ��

     FileCopy strTempFile, Location

   

     ' ɾ����ʱ�ļ�

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

