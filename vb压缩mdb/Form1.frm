VERSION 5.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   10440
   ClientLeft      =   60
   ClientTop       =   405
   ClientWidth     =   16605
   LinkTopic       =   "Form1"
   ScaleHeight     =   10440
   ScaleWidth      =   16605
   StartUpPosition =   3  '´°¿ÚÈ±Ê¡
   Begin VB.TextBox Text1 
      Height          =   1455
      Left            =   5760
      TabIndex        =   1
      Text            =   "Text1"
      Top             =   1800
      Width           =   3015
   End
   Begin VB.CommandButton Command1 
      Caption         =   "Command1"
      Height          =   975
      Left            =   6120
      TabIndex        =   0
      Top             =   6240
      Width           =   1815
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub Command1_Click()
Module1.CompactJetDatabase App.Path + "\bb.mdb"
End Sub

