package denkoviservidor;
//<editor-fold desc="Imports">
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import java.util.Calendar;
//</editor-fold>

public class FXMLDocumentController implements Initializable {
        
    //<editor-fold desc="variables">
        //variables timer
        long starttimeNano = 0,lasttimeFPS = 0;
        int frameCnt = 0,measurementTest = 0;
    
        //variables del socket
        String consulta="";
        Socket clientSocket,clientSocket1;;
        PrintWriter outToServer,outToServer1;
        BufferedReader inFromServer,inFromServer1;
        char[] Answer = new char[50];
        char[] Answer1 = new char[50];  
        int decAnswer,decAnswer1;   
    
        //fecha
        Calendar fecha ;
        
        // contadores para cuando oprimo un rele y lo suelto
        int contD1_1=0,contD1_2=0,contD1_3=0,contD1_4=0,contD1_5=0,contD1_6=0,contD1_7=0,contD1_8=0;
        int contD2_1=0,contD2_2=0,contD2_3=0,contD2_4=0,contD2_5=0,contD2_6=0,contD2_7=0,contD2_8=0;

        //estadisticas de los denkovis
        int est1_1=0,est1_2=0,est1_3=0,est1_4=0;
        int est2_1=0,est2_2=0,est2_3=0,est2_4=0;
        int est3_1=0,est3_2=0,est3_3=0,est3_4=0;
        int est4_1=0;
                
        int PT1,PR1,PI1,PO1,PD1,CA1;//variables pestaña reporte -> sotano 1
        int PT2,PR2,PI2,PO2,PD2,CA2;//variables pestaña reporte -> sotano 2
        int PT3,PR3,PI3,PO3,PD3,CA3;//variables pestaña reporte -> sotano 3
        int PT4,PR4,PI4,PO4,PD4,CA4;//variables pestaña reporte -> sotano 4
    
        int PDI1,PDI2,PDI3,PDI4;//puestos disponibles al iniciar el sistema
        
        boolean conectado=true; //bandera cuando conecto y dsconecto conexion
    
        @FXML   private TextField SA, SA1, PA, PA1, DA, DA1 ;
        @FXML   Button Asignar, Conectar, Desconectar,iniciar,cerrar;//botones
        @FXML   private Label    E1,E11,E2,E21,E3,E31,E4,E41,E5,E51,E6,E61,E7,E71,E8,E81; // label de los 2 denkovi Enc/Apa
        @FXML   private Label    R1_1,R1_2,R1_3,R1_4,R1_5; //estadisticas reporte S1
        @FXML   private Label    R2_1,R2_2,R2_3,R2_4,R2_5,R2_6; //estadisticas reporte S2   
        @FXML   private Label    R3_1,R3_2,R3_3,R3_4,R3_5,R3_6; //estadisticas reporte S3
        @FXML   private Label    R4_1,R4_2;                //estadisticas reporte S4
        @FXML   private Label    msj_sql,msj_error,msj_exito; 
        @FXML   private Label    Cant_Puestos1,Cant_Puestos2,Cant_Puestos3,Cant_Puestos4;    // cantidad puestos totales
        @FXML   private Label    Cant_PuestosD1,Cant_PuestosD2,Cant_PuestosD3,Cant_PuestosD4;// cantidad puestos disponibles
        @FXML   private Label    Cant_PuestosR1,Cant_PuestosR2,Cant_PuestosR3,Cant_PuestosR4;// cantidad puestos reservados
        @FXML   private Label    Cant_PuestosO1,Cant_PuestosO2,Cant_PuestosO3,Cant_PuestosO4;// cantidad puestos ocupados
        @FXML   private Label    Cant_PuestosI1,Cant_PuestosI2,Cant_PuestosI3,Cant_PuestosI4;// cantidad puestos inicial
        @FXML   private Label    Cant_Autos1,Cant_Autos2,Cant_Autos3,Cant_Autos4;            // cantidad autos sotano
        @FXML   private PieChart chart1,chart2,chart3,chart4,chart1234; // graficas
        @FXML   private Tab      principal_panel,Denkovi_panel,Reportes_panel,ReporteGrafica_panel,Reporte1_panel,Reporte2_panel,Reporte3_panel,Reporte4_panel; //pestañas
        @FXML   private TextField Puestos_Totales1,Puestos_Totales2,Puestos_Totales3,Puestos_Totales4;
        @FXML   private TextField Puestos_Reservados1,Puestos_Reservados2,Puestos_Reservados3,Puestos_Reservados4;
        @FXML   private TextField login,Puestos_ocupado1,Puestos_ocupado2,Puestos_ocupado3,Puestos_ocupado4;
        @FXML   private PasswordField password;

    //</editor-fold>

    @FXML void asignarS1(ActionEvent event) throws SQLException, ClassNotFoundException //cuando oprimo el boton asignar
    {
       
        //<editor-fold desc="Sotano 1">        
        
            //<editor-fold desc="puestos totales">
            PT1=Integer.parseInt(Puestos_Totales1.getText());
            consulta="UPDATE estacionamiento SET Capacidad='"+PT1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);       
            Cant_Puestos1.setText(""+PT1);
            //</editor-fold>
           
            //<editor-fold desc="puestos reservados">
            PR1=Integer.parseInt(Puestos_Reservados1.getText());
            consulta="UPDATE estacionamiento SET Puestos_reservados='"+PR1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);  
            Cant_PuestosR1.setText(""+PR1);
            //</editor-fold>
            
            //<editor-fold desc="puestos ocupados">
            PO1=Integer.parseInt(Puestos_ocupado1.getText());
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO1.setText(""+PO1);
            CA1=PO1;
            Cant_Autos1.setText(""+CA1);
            consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            //</editor-fold>
            
            //<editor-fold desc="puestos Inicial">
            PI1=PT1-PR1;
            if(PI1<0){PI1=0;}
            consulta="UPDATE estacionamiento SET Puestos_inicial='"+PI1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosI1.setText(""+PI1);            
            PDI1=PI1;
            //</editor-fold>
            
            //<editor-fold desc="puestos Disponibles">
            PD1=PI1-PO1;
            if(PD1<0){PD1=0;}
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD1.setText(""+PD1);
            //</editor-fold>
            

            
        //</editor-fold>
        
        //<editor-fold desc="Sotano 2">
            
            //<editor-fold desc="puestos totales">
            PT2=Integer.parseInt(Puestos_Totales2.getText());
            consulta="UPDATE estacionamiento SET Capacidad='"+PT2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta); 
            Cant_Puestos2.setText(""+PT2);
            //</editor-fold>
            
            //<editor-fold desc="puestos reservados">
            PR2=Integer.parseInt(Puestos_Reservados2.getText());
            consulta="UPDATE estacionamiento SET Puestos_reservados='"+PR2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);  
            Cant_PuestosR2.setText(""+PR2);
            //</editor-fold>
            
            //<editor-fold desc="puestos ocupados">
            PO2=Integer.parseInt(Puestos_ocupado2.getText());
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO2.setText(""+PO2);
            CA2=PO2;
            Cant_Autos2.setText(""+CA2);
            consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            //</editor-fold>
            
            //<editor-fold desc="puestos Inicial">
            PI2=PT2-PR2;
            if(PI2<0){PI2=0;}
            consulta="UPDATE estacionamiento SET Puestos_inicial='"+PI2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosI2.setText(""+PI2);
            PDI2=PI2;
            //</editor-fold>
            
            //<editor-fold desc="puestos Disponibles">
            PD2=PI2-PO2;
            if(PD2<0){PD2=0;}
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD2.setText(""+PD2);
            //</editor-fold>
            
       //</editor-fold>
        
        //<editor-fold desc="Sotano 3">
            
            //<editor-fold desc="puestos totales">
            PT3=Integer.parseInt(Puestos_Totales3.getText());
            consulta="UPDATE estacionamiento SET Capacidad='"+PT3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta); 
            Cant_Puestos3.setText(""+PT3);
            //</editor-fold>
            
            //<editor-fold desc="puestos reservados">
            PR3=Integer.parseInt(Puestos_Reservados3.getText());
            consulta="UPDATE estacionamiento SET Puestos_reservados='"+PR3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);  
            Cant_PuestosR3.setText(""+PR3);
            //</editor-fold>
            
            //<editor-fold desc="puestos ocupados">
            PO3=Integer.parseInt(Puestos_ocupado3.getText());
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO3.setText(""+PO3);
            CA3=PO3;
            Cant_Autos3.setText(""+CA3);
            consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            //</editor-fold>
            
            //<editor-fold desc="puestos Inicial">
            PI3=PT3-PR3;
            if(PI3<0){PI3=0;}
            consulta="UPDATE estacionamiento SET Puestos_inicial='"+PI3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosI3.setText(""+PI3);
            PDI3=PI3;
            //</editor-fold>
            
            //<editor-fold desc="puestos Disponibles">
            PD3=PI3-PO3;
            if(PD3<0){PD3=0;}
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD3.setText(""+PD3);
            //</editor-fold>
            
       //</editor-fold>
            
        //<editor-fold desc="Sotano 4">
            
            //<editor-fold desc="puestos totales">
            PT4=Integer.parseInt(Puestos_Totales4.getText());
            consulta="UPDATE estacionamiento SET Capacidad='"+PT4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta); 
            Cant_Puestos4.setText(""+PT4);
            //</editor-fold>
            
            //<editor-fold desc="puestos reservados">
            PR4=Integer.parseInt(Puestos_Reservados4.getText());
            consulta="UPDATE estacionamiento SET Puestos_reservados='"+PR4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta);  
            Cant_PuestosR4.setText(""+PR4);
            //</editor-fold>
            
            //<editor-fold desc="puestos ocupados">
            PO4=Integer.parseInt(Puestos_ocupado4.getText());
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO4.setText(""+PO4);
            CA4=PO4;
            Cant_Autos4.setText(""+CA4);
            consulta="UPDATE estacionamiento SET trafico='"+CA4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta);
            //</editor-fold>
            
            //<editor-fold desc="puestos Inicial">
            PI4=PT4-PR4;
            if(PI4<0){PI4=0;}
            consulta="UPDATE estacionamiento SET Puestos_inicial='"+PI4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosI4.setText(""+PI4);
            PDI4=PI4;
            //</editor-fold>
            
            //<editor-fold desc="puestos Disponibles">
            PD4=PI4-PO4;
            if(PD4<0){PD4=0;}
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD4+"' WHERE idEstacionamiento='4'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD4.setText(""+PD4);
            //</editor-fold>
            
       //</editor-fold>
            
        //<editor-fold desc="Deshabilitar componentes"> 
       // Asignar.setDisable(true);        
        Desconectar.setDisable(true);
        Denkovi_panel.setDisable(false);
        fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        mes=(mes+1);
        
        if(hora>12){hora=hora-12;} 
        String hor=""+hora;
        String min=""+minuto;
        String seg=""+segundo;
        String dia1=""+dia;
        String mes1=""+mes;
        String anio1=""+anio;
        
        if(hora<10){hor="0"+hora;}
        if(minuto<10){min="0"+minuto;}
        if(segundo<10){seg="0"+segundo;}
        if(dia<10){dia1="0"+dia;}
        if(mes<10){mes1="0"+mes;}
        if(anio<10){anio1="0"+anio;}
        
        
               

        msj_sql.setText("Datos registrados el "+dia1+"/"+mes1+"/"+anio1+" a las "+hor+":"+min+":"+seg);
        /*Puestos_Totales1.setDisable(true);
        Puestos_Reservados1.setDisable(true);
        Puestos_ocupado1.setDisable(true);

        Puestos_Totales2.setDisable(true);
        Puestos_Reservados2.setDisable(true);
        Puestos_ocupado2.setDisable(true);
        
        Puestos_Totales3.setDisable(true);
        Puestos_Reservados3.setDisable(true);
        Puestos_ocupado3.setDisable(true);
        
        Puestos_Totales4.setDisable(true);
        Puestos_Reservados4.setDisable(true);
        Puestos_ocupado4.setDisable(true);*/
        //</editor-fold>
    }
    
    @FXML private void handleButtonAction2(ActionEvent event) throws IOException //cuando oprimo el boton desconectar
    {
        conectado=false;
        
        Conectar.setDisable(false);
        Desconectar.setDisable(true);
        
        SA.setDisable(false);
        DA.setDisable(false);
        PA.setDisable(false);
                        
        SA1.setDisable(false);
        DA1.setDisable(false);
        PA1.setDisable(false);
    }

    @FXML private void handleButtonAction1(ActionEvent event) throws IOException //cuando oprimo el boton conectar
    {
        //<editor-fold desc="habilitar complementos">    
        Reportes_panel.setDisable(false);
        Denkovi_panel.setDisable(false);
        ReporteGrafica_panel.setDisable(false);
        Reporte1_panel.setDisable(false);
        Reporte2_panel.setDisable(false);
        Reporte3_panel.setDisable(false);
        Reporte4_panel.setDisable(false);
        
        Conectar.setDisable(true);
        Desconectar.setDisable(false);
                        
        SA.setDisable(true);
        DA.setDisable(true);
        PA.setDisable(true);
                        
        SA1.setDisable(true);
        DA1.setDisable(true);
        PA1.setDisable(true);
        //</editor-fold>
        
        conectado = true;        
        
        new AnimationTimer()
        {
            @Override
            public void handle(long arg0)
            {
		long currenttimeNano = System.nanoTime();
          	if (currenttimeNano > lasttimeFPS + 100000000 && conectado==true)
               	{
                    try 
                    {
                        GetDO();
                    } catch (IOException | SQLException | ClassNotFoundException ex) 
                    {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frameCnt = 0;
                    lasttimeFPS = currenttimeNano;
                }
            }
        }.start();
    }
    
    @Override   public void initialize(URL url, ResourceBundle rb) 
    {
       
    }    
    
    @SuppressWarnings("empty-statement")
    public void GetDO() throws IOException, SQLException, ClassNotFoundException
    {
        //<editor-fold desc="configuracion de conexion">
        clientSocket = new Socket(DA.getText(), Integer.parseInt(PA.getText()));
        clientSocket1 = new Socket(DA1.getText(), Integer.parseInt(PA1.getText()));
        
        clientSocket.setSoTimeout(5000);
        clientSocket1.setSoTimeout(5000);
        
        outToServer = new PrintWriter(clientSocket.getOutputStream(),true);
        outToServer1 = new PrintWriter(clientSocket1.getOutputStream(),true);
        
        outToServer.write(SA.getText()+"BVG=?;");
        outToServer1.write(SA1.getText()+"BVG=?;");
        
        
        if(outToServer.checkError());
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        inFromServer.read(Answer);  
        decAnswer=Integer.parseInt(String.valueOf(Answer).substring(6, 8),16);
               
        if(outToServer1.checkError());
        inFromServer1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
        inFromServer1.read(Answer1);  
        decAnswer1=Integer.parseInt(String.valueOf(Answer1).substring(6, 8),16);
        //</editor-fold>
        
        //<editor-fold desc="Denkovi1"> 
        if ((decAnswer & 1)==0)     {E1.setText("Apagado");contD1_1=0;}else {E1.setText("Encendido");contD1_1++;}   //Entrada Barrera Sur  S1             
        if ((decAnswer & 2)==0)     {E2.setText("Apagado");contD1_2=0;}else {E2.setText("Encendido");contD1_2++;}   //Entrada Barrera Norte S1
        if ((decAnswer & 4)==0)     {E3.setText("Apagado");contD1_3=0;}else {E3.setText("Encendido");contD1_3++;}   //Salida Barrera Norte S1
        if ((decAnswer & 8)==0)     {E4.setText("Apagado");contD1_4=0;}else {E4.setText("Encendido");contD1_4++;}   //Salida a Sotano 2 (Radar)S1
        if ((decAnswer & 16)==0)    {E5.setText("Apagado");contD1_5=0;}else {E5.setText("Encendido");contD1_5++;}   //Entrada Barrera S2
        if ((decAnswer & 32)==0)    {E6.setText("Apagado");contD1_6=0;}else {E6.setText("Encendido");contD1_6++;}   //Salida Barrera S2
        if ((decAnswer & 64)==0)    {E7.setText("Apagado");contD1_7=0;}else {E7.setText("Encendido");contD1_7++;}   //Salida a Sotano 1 (Radar)S2
        if ((decAnswer & 128)==0)   {E8.setText("Apagado");contD1_8=0;}else {E8.setText("Encendido");contD1_8++;}   //Salida a Sotano 3 (Radar)S2
        //</editor-fold>
        
        //<editor-fold desc="Denkovi2"> 
        if ((decAnswer1 & 1)==0)    {E11.setText("Apagado");contD2_1=0;}else   {E11.setText("Encendido");contD2_1++;}   //Entrada Barrera S3             
        if ((decAnswer1 & 2)==0)    {E21.setText("Apagado");contD2_2=0;}else   {E21.setText("Encendido");contD2_2++;}   //Salida Barrera S3
        if ((decAnswer1 & 4)==0)    {E31.setText("Apagado");contD2_3=0;}else   {E31.setText("Encendido");contD2_3++;}   //Salida a Sotano 2 (Radar) S3
        if ((decAnswer1 & 8)==0)    {E41.setText("Apagado");contD2_4=0;}else   {E41.setText("Encendido");contD2_4++;}   //Salida a Sotano 4 (Radar) S3
        if ((decAnswer1 & 16)==0)   {E51.setText("Apagado");contD2_5=0;}else   {E51.setText("Encendido");contD2_5++;}   //Salida a Sotano 3 (Radar) S4
        if ((decAnswer1 & 32)==0)   {E61.setText("Apagado");contD2_6=0;}else   {E61.setText("Encendido");contD2_6++;}   //
        if ((decAnswer1 & 64)==0)   {E71.setText("Apagado");contD2_7=0;}else   {E71.setText("Encendido");contD2_7++;}   //
        if ((decAnswer1 & 128)==0)  {E81.setText("Apagado");contD2_8=0;}else   {E81.setText("Encendido");contD2_8++;}   //
        //</editor-fold>
        
        //<editor-fold desc="cierre de conexion"> 
        inFromServer.close();
        inFromServer1.close();
        
        outToServer.close();
        outToServer1.close();
        
        clientSocket.close();
        clientSocket1.close();       
        //</editor-fold>
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
       
       //<editor-fold desc="1_1)Entrada Barrera Sur S1"> 
        if(contD1_1==1)
        {
            PD1--;
            CA1++;
            PO1++;
            
            est1_1++;
            R1_1.setText(""+est1_1);
            
            //puestos disponibles
            if(PD1<0){PD1=0;}            
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD1.setText(""+PD1);
            
            
            //puestos ocupados
            if(PO1>PDI1){PO1=PDI1;}
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO1.setText(""+PO1);
            
            //cantidad de autos
            Cant_Autos1.setText(""+CA1);
            consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold>
        
       //<editor-fold desc="1_2)Entrada Barrera norte S1"> 
        if(contD1_2==1)
        {
            PD1--;
            CA1++;
            PO1++;
            
            est1_2++;
            R1_2.setText(""+est1_2);
            
            //puestos disponibles
            if(PD1<0){PD1=0;}            
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD1.setText(""+PD1);            
            
            //puestos ocupados
            if(PO1>PDI1){PO1=PDI1;}
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO1.setText(""+PO1);
            
            //cantidad de autos
            Cant_Autos1.setText(""+CA1);
            consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold>        

       //<editor-fold desc="1_3)Salida Barrera Norte S1"> 
        if(contD1_3==1)
        {
            est1_3++;
            R1_3.setText(""+est1_3);
            
            CA1--;    
                    
            if(CA1<PI1)
            {
                //puestos disponibles
                PD1++;
                if(PD1>PI1){PD1=PI1;}
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD1.setText(""+PD1);
                
                //puesto ocupados              
                PO1--;
                if(PO1<0){PO1=0;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO1.setText(""+PO1);
            }

            //cantidad de autos
            if(CA1<0){CA1=0;}
            Cant_Autos1.setText(""+CA1);
            consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold>

       //<editor-fold desc="1_4)Salida a Sotano 2 (Radar)S1"> 
        if(contD1_4==1)
        {
            est1_4++;
            R1_4.setText(""+est1_4);
            R2_5.setText(""+est1_4);
            
            //<editor-fold desc="sotano 1"> 
                CA1--;    
                if(CA1<PI1)
                {
                    //puestos disponibles
                    PD1++;
                    if(PD1>PI1){PD1=PI1;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD1.setText(""+PD1);

                    //puesto ocupados              
                    PO1--;
                    if(PO1<0){PO1=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO1.setText(""+PO1);
                }

                //cantidad de autos
                if(CA1<0){CA1=0;}
                Cant_Autos1.setText(""+CA1);
                consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
            
            //</editor-fold>    
            
            //<editor-fold desc="Sotano 2"> 
                PD2--;
                CA2++;
                PO2++;

                //puestos disponibles
                if(PD2<0){PD2=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD2.setText(""+PD2);

                //puestos ocupados
                if(PO2>PDI2){PO2=PDI2;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO2.setText(""+PO2);

                //cantidad de autos
                Cant_Autos2.setText(""+CA2);
                consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold>
        }
        //</editor-fold>
        
       //<editor-fold desc="1_5)Entrada Barrera S2"> 
        if(contD1_5==1)
        {
            PD2--;
            CA2++;
            PO2++;

            est2_1++;
            R2_1.setText(""+est2_1);
            
            //puestos disponibles
            if(PD2<0){PD2=0;}            
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD2.setText(""+PD2);

            //puestos ocupados
            if(PO2>PDI2){PO2=PDI2;}
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO2.setText(""+PO2);

            //cantidad de autos
            Cant_Autos2.setText(""+CA2);
            consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold>
        
       //<editor-fold desc="1_6)Salida Barrera S2"> 
        if(contD1_6==1)
        {
            est2_2++;
            R2_2.setText(""+est2_2);
            
            CA2--;                    
            if(CA2<PI2)
            {
                //puestos disponibles
                PD2++;
                if(PD2>PI2){PD2=PI2;}
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD2.setText(""+PD2);
                
                //puesto ocupados              
                PO2--;
                if(PO2<0){PO2=0;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO2.setText(""+PO2);
            }

            //cantidad de autos
            if(CA2<0){CA2=0;}
            Cant_Autos2.setText(""+CA2);
            consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
            ConexionDb.GetConnection(consulta);
            
        }
        //</editor-fold>
        
       //<editor-fold desc="1_7)Salida a Sotano 1 (Radar)S2"> 
        if(contD1_7==1)
        {
            est2_3++;
            R2_3.setText(""+est2_3);
            R1_5.setText(""+est2_3);
                    
            //<editor-fold desc="Sotano 1"> 
                PD1--;
                CA1++;
                PO1++;

                //puestos disponibles
                if(PD1<0){PD1=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD1.setText(""+PD1);


                //puestos ocupados
                if(PO1>PDI1){PO1=PDI1;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO1.setText(""+PO1);

                //cantidad de autos
                Cant_Autos1.setText(""+CA1);
                consulta="UPDATE estacionamiento SET trafico='"+CA1+"' WHERE idEstacionamiento='1'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold> 

            //<editor-fold desc="Sotano 2"> 
                CA2--;                    
                if(CA2<PI2)
                {
                    //puestos disponibles
                    PD2++;
                    if(PD2>PI2){PD2=PI2;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD2.setText(""+PD2);

                    //puesto ocupados              
                    PO2--;
                    if(PO2<0){PO2=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO2.setText(""+PO2);
                }

                //cantidad de autos
                if(CA2<0){CA2=0;}
                Cant_Autos2.setText(""+CA2);
                consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold> 

        }
        //</editor-fold> 
        
       //<editor-fold desc="1_8)Salida a Sotano 3 (Radar)S2"> 
        if(contD1_8==1)
        {
            est2_4++;
            R2_4.setText(""+est2_4);
            R3_5.setText(""+est2_4);
            
            //<editor-fold desc="sotano 2"> 
                CA2--;                    
                if(CA2<PI2)
                {
                    //puestos disponibles
                    PD2++;
                    if(PD2>PI2){PD2=PI2;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD2.setText(""+PD2);

                    //puesto ocupados              
                    PO2--;
                    if(PO2<0){PO2=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO2.setText(""+PO2);
                }

                //cantidad de autos
                if(CA2<0){CA2=0;}
                Cant_Autos2.setText(""+CA2);
                consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
            
            //</editor-fold>   
            
            //<editor-fold desc="Sotano 3"> 
                PD3--;
                CA3++;
                PO3++;

                //puestos disponibles
                if(PD3<0){PD3=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD3.setText(""+PD3);


                //puestos ocupados
                if(PO3>PDI3){PO3=PDI3;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO3.setText(""+PO3);

                //cantidad de autos
                Cant_Autos3.setText(""+CA3);
                consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold>
        }
        //</editor-fold>  
        
       //<editor-fold desc="2_1)Entrada Barrera S3"> 
        if(contD2_1==1)
        {
            est3_1++;
            R3_1.setText(""+est3_1);
            
            PD3--;
            CA3++;
            PO3++;

            //puestos disponibles
            if(PD3<0){PD3=0;}            
            consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosD3.setText(""+PD3);

            //puestos ocupados
            if(PO3>PDI3){PO3=PDI3;}
            consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
            Cant_PuestosO3.setText(""+PO3);

            //cantidad de autos
            Cant_Autos3.setText(""+CA3); 
            consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold> 
        
       //<editor-fold desc="2_2)Salida Barrera S3"> 
        if(contD2_2==1)
        {
            est3_2++;
            R3_2.setText(""+est3_2);
            
            CA3--;                    
            if(CA3<PI3)
            {
                //puestos disponibles
                PD3++;
                if(PD3>PI3){PD3=PI3;}
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD3.setText(""+PD3);

                //puesto ocupados              
                PO3--;
                if(PO3<0){PO3=0;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO3.setText(""+PO3);
            }

            //cantidad de autos
            if(CA3<0){CA3=0;}
            Cant_Autos3.setText(""+CA3);
            consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
            ConexionDb.GetConnection(consulta);
        }
        //</editor-fold> 
        
       //<editor-fold desc="2_3)Salida a Sotano 2 (Radar) S3"> 
        if(contD2_3==1)
        {
            est3_3++;
            R3_3.setText(""+est3_3);
            R2_6.setText(""+est3_3);
            
            //<editor-fold desc="sotano 3"> 
                CA3--;                    
                if(CA3<PI3)
                {
                    //puestos disponibles
                    PD3++;
                    if(PD3>PI3){PD3=PI3;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD3.setText(""+PD3);

                    //puesto ocupados              
                    PO3--;
                    if(PO3<0){PO3=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO3.setText(""+PO3);
                }

                //cantidad de autos
                if(CA3<0){CA3=0;}
                Cant_Autos3.setText(""+CA3); 
                consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold> 

            //<editor-fold desc="Sotano 2"> 
                PD2--;
                CA2++;
                PO2++;

                //puestos disponibles
                if(PD2<0){PD2=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD2.setText(""+PD2);

                //puestos ocupados
                if(PO2>PDI2){PO2=PDI2;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO2.setText(""+PO2);

                //cantidad de autos
                Cant_Autos2.setText(""+CA2);
                consulta="UPDATE estacionamiento SET trafico='"+CA2+"' WHERE idEstacionamiento='2'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold>
        }
        //</editor-fold> 
       
       //<editor-fold desc="2_4)Salida a Sotano 4 (Radar) S3"> 
        if(contD2_4==1)
        {
            est3_4++;
            R3_4.setText(""+est3_4);
            R4_2.setText(""+est3_4);
            //<editor-fold desc="sotano 3"> 
                CA3--;                    
                if(CA3<PI3)
                {
                    //puestos disponibles
                    PD3++;
                    if(PD3>PI3){PD3=PI3;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD3.setText(""+PD3);

                    //puesto ocupados              
                    PO3--;
                    if(PO3<0){PO3=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO3.setText(""+PO3);
                }

                //cantidad de autos
                if(CA3<0){CA3=0;}
                Cant_Autos3.setText(""+CA3);    
                consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold> 
            
            //<editor-fold desc="Sotano 4"> 
                PD4--;
                CA4++;
                PO4++;

                //puestos disponibles
                if(PD4<0){PD4=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD4+"' WHERE idEstacionamiento='4'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD4.setText(""+PD4);

                //puestos ocupados
                if(PO4>PDI4){PO4=PDI4;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO4+"' WHERE idEstacionamiento='4'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO4.setText(""+PO4);

                //cantidad de autos
                Cant_Autos4.setText(""+CA4);
                consulta="UPDATE estacionamiento SET trafico='"+CA4+"' WHERE idEstacionamiento='4'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold>
        }
        //</editor-fold> 
       
       //<editor-fold desc="2_5)Salida a Sotano 3 (Radar) S4"> 
        if(contD2_5==1)
        {
            est4_1++;
            R4_1.setText(""+est4_1);
            R3_6.setText(""+est4_1);
            //<editor-fold desc="Sotano 3"> 
                PD3--;
                CA3++;
                PO3++;

                //puestos disponibles
                if(PD3<0){PD3=0;}            
                consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosD3.setText(""+PD3);

                //puestos ocupados
                if(PO3>PDI3){PO3=PDI3;}
                consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
                Cant_PuestosO3.setText(""+PO3);

                //cantidad de autos
                Cant_Autos3.setText(""+CA3);
                consulta="UPDATE estacionamiento SET trafico='"+CA3+"' WHERE idEstacionamiento='3'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold>
            
            //<editor-fold desc="sotano 4"> 
                CA4--;                    
                if(CA4<PI4)
                {
                    //puestos disponibles
                    PD4++;
                    if(PD4>PI4){PD4=PI4;}
                    consulta="UPDATE estacionamiento SET Puestos_disponibles='"+PD4+"' WHERE idEstacionamiento='4'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosD4.setText(""+PD4);

                    //puesto ocupados              
                    PO4--;
                    if(PO4<0){PO4=0;}
                    consulta="UPDATE estacionamiento SET Puestos_ocupados='"+PO4+"' WHERE idEstacionamiento='4'";
                    ConexionDb.GetConnection(consulta);
                    Cant_PuestosO4.setText(""+PO4);
                }

                //cantidad de autos
                if(CA4<0){CA4=0;}
                Cant_Autos4.setText(""+CA4); 
                consulta="UPDATE estacionamiento SET trafico='"+CA4+"' WHERE idEstacionamiento='4'";
                ConexionDb.GetConnection(consulta);
            //</editor-fold> 
        }
        //</editor-fold> 
        
       
       //<editor-fold desc="chart1"> 
        if((contD1_1==1||contD1_2==1||contD1_3==1||contD1_4==1||contD1_7==1))
        {
            ObservableList<PieChart.Data> chart1Data = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Puestos Disponibles: "+PD1, PD1),
                    new PieChart.Data("Puestos Ocupados: "+PO1, PO1));
         
             chart1.setData(chart1Data);
        }
        //</editor-fold> 
        
       //<editor-fold desc="chart2"> 
        if(contD1_4==1||contD1_5==1||contD1_6==1||contD1_7==1||contD1_8==1||contD2_3==1)
        {
            ObservableList<PieChart.Data> chart1Data = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Puestos Disponibles: "+PD2, PD2),
                    new PieChart.Data("Puestos Ocupados: "+PO2, PO2));
         
             chart2.setData(chart1Data);
        }
        //</editor-fold> 
        
       //<editor-fold desc="chart3"> 
        if(contD2_1==1||contD2_2==1||contD2_3==1||contD2_4==1||contD1_8==1||contD2_3==1)
        {
            ObservableList<PieChart.Data> chart1Data = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Puestos Disponibles: "+PD3, PD3),
                    new PieChart.Data("Puestos Ocupados: "+PO3, PO3));
         
             chart3.setData(chart1Data);
        }
        //</editor-fold> 
       
       //<editor-fold desc="chart4"> 
        if(contD2_4==1||contD2_5==1)
        {
            ObservableList<PieChart.Data> chart1Data = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Puestos Disponibles: "+PD4, PD4),
                    new PieChart.Data("Puestos Ocupados: "+PO4, PO4));
         
             chart4.setData(chart1Data);
        }
        //</editor-fold> 
       
       //<editor-fold desc="chart1234"> 
       if(contD1_1==1||contD1_2==1||contD1_3==1||contD1_4==1||contD1_5==1||contD1_6==1||contD1_7==1||contD1_8==1||contD2_1==1||contD2_2==1||contD2_3==1||contD2_4==1||contD2_5==1)
        {
            ObservableList<PieChart.Data> chart1Data = 
                FXCollections.observableArrayList(
                    new PieChart.Data("Puestos Disponibles: "+(PD1+PD2+PD3+PD4),(PD1+PD2+PD3+PD4)),
                    new PieChart.Data("Puestos Ocupados: "+(PO1+PO2+PO3+PO4), (PO1+PO2+PO3+PO4)));
         
             chart1234.setData(chart1Data);
        }
       //</editor-fold> 
    }

    @FXML
    void iniciar_sesion(ActionEvent event) throws SQLException, ClassNotFoundException
    {
        String usuario = login.getText();
        String clave = password.getText();
        boolean valido=ConexionDb.validar_sesion(usuario, clave);
        
        if(valido==true)
        {
            principal_panel.setDisable(false);
            Denkovi_panel.setDisable(false);
            login.clear();
            password.clear();
            iniciar.setDisable(true);
            cerrar.setDisable(false);
            msj_error.setText("");
            msj_exito.setText("EXITO! Bienvenido Administrador!");
        }else
        {
            msj_exito.setText("");
            msj_error.setText("ERROR! Usuario o Clave Invalida!");
        }
    }

    @FXML
    void cerrar_sesion(ActionEvent event) 
    {
        principal_panel.setDisable(true);
        Denkovi_panel.setDisable(true);
        iniciar.setDisable(false);
        cerrar.setDisable(true);
    }
}
