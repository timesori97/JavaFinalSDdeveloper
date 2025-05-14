package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import membership.MembershipDTO;

public class QuaryDAOGui extends JFrame implements ActionListener {
   private JTextArea queryInput;
   private JButton executeButton;
   private JButton modButton;
   private JButton exportButton;
   private JTable resultTable;
   private DefaultTableModel tableModel;
   private JLabel messageLabel;
   private MembershipDTO member;

   public QuaryDAOGui(MembershipDTO member) {
      this.member = member;
      setTitle("SD Developer");
      setSize(900, 600);
      setLocationRelativeTo(null);
      setLayout(new BorderLayout(10, 10));
      getContentPane().setBackground(Color.WHITE);
      setResizable(false); // 창 크기 변경 불가

      // 상단 패널
      JPanel topPanel = new JPanel(new BorderLayout(5, 5));
      topPanel.setBackground(new Color(245, 245, 245));
      topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

      // 텍스트 영역을 감싸는 스크롤 패널 추가
      queryInput = new JTextArea(5, 40);
      queryInput.setBackground(Color.WHITE);
      queryInput.setForeground(Color.BLACK);
      queryInput.setCaretColor(Color.BLACK);
      queryInput.setFont(new Font("맑은 고딕", Font.PLAIN, 14)); // 한국어 지원 폰트로 설정
      queryInput.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

      // 스크롤 패널로 감싸기
      JScrollPane queryScrollPane = new JScrollPane(queryInput);
      queryInput.setWrapStyleWord(true); // 줄바꿈 처리
      queryInput.setLineWrap(true); // 자동 줄바꿈
      topPanel.add(queryScrollPane, BorderLayout.CENTER);

      modButton = new JButton("아이디/비밀번호 수정");
      modButton.setBounds(700, 530, 180, 30);
      add(modButton);

      executeButton = new JButton("쿼리문 실행");
      executeButton.setBackground(new Color(0, 122, 204));
      executeButton.setForeground(Color.WHITE);
      executeButton.setFocusPainted(false);
      executeButton.setFont(new Font("맑은 고딕", Font.BOLD, 14)); // 한국어 지원 폰트로 설정
      executeButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
      topPanel.add(executeButton, BorderLayout.EAST);

      // Add export button
      exportButton = new JButton("엑셀로 저장");
      exportButton.setBackground(new Color(0, 122, 204));
      exportButton.setForeground(Color.WHITE);
      exportButton.setFocusPainted(false);
      exportButton.setFont(new Font("맑은 고딕", Font.BOLD, 14)); // 한국어 지원 폰트로 설정
      exportButton.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
      topPanel.add(exportButton, BorderLayout.WEST);

      add(topPanel, BorderLayout.NORTH);

      // 결과 테이블 패널
      tableModel = new DefaultTableModel();
      resultTable = new JTable(tableModel);
      resultTable.setBackground(Color.WHITE);
      resultTable.setForeground(Color.BLACK);
      resultTable.setFont(new Font("맑은 고딕", Font.PLAIN, 14)); // 한국어 지원 폰트로 설정
      resultTable.setRowHeight(25);
      resultTable.setGridColor(new Color(220, 220, 220));
      resultTable.setSelectionBackground(new Color(0, 122, 204));
      resultTable.setSelectionForeground(Color.WHITE);
      resultTable.setDefaultEditor(Object.class, null);

      JTableHeader header = resultTable.getTableHeader();
      header.setBackground(new Color(240, 240, 240));
      header.setForeground(Color.BLACK);
      header.setFont(new Font("맑은 고딕", Font.BOLD, 14)); // 한국어 지원 폰트로 설정

      JScrollPane tableScrollPane = new JScrollPane(resultTable);
      
      add(tableScrollPane, BorderLayout.CENTER);

      // 메시지 출력 레이블
      messageLabel = new JLabel(" ");
      messageLabel.setForeground(Color.RED);
      messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14)); // 한국어 지원 폰트로 설정
      messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
      messageLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));
      add(messageLabel, BorderLayout.SOUTH);

      executeButton.addActionListener(this);
      modButton.addActionListener(this);
      exportButton.addActionListener(this); // Add action listener for the export button

      setVisible(true);
   }

   // 데이터베이스 연결 함수
   private Connection getConn() throws SQLException {
      String url = "jdbc:oracle:thin:@localhost:1521:orcl";

      String user = member.getId();
      String password = member.getPass();
      return DriverManager.getConnection(url, user, password);
   }

   // 쿼리 실행 함수
   public void executeQuery() {
      String sql = queryInput.getText().trim();

      PreparedStatement stmt = null;
      ResultSet rs = null;
      CallableStatement cstmt = null;
     
     

      try (Connection conn = getConn()) {
         if (sql.toLowerCase().startsWith("select")) {
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            // 테이블 초기화
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            // 메타데이터에서 컬럼명 추가
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
               tableModel.addColumn(metaData.getColumnName(i));
            }

            // 결과 추가
            while (rs.next()) {
               Object[] rowData = new Object[columnCount];
               for (int i = 1; i <= columnCount; i++) {
                  rowData[i - 1] = rs.getObject(i);
               }
               tableModel.addRow(rowData);
            }
            showMessage("쿼리 실행 성공");
         }else if(sql.toLowerCase().startsWith("explain plan")) {
        	 stmt = conn.prepareStatement(sql);
        	 rs = stmt.executeQuery();
        	 
        	 if(rs != null) {
        		 showMessage("설명되었습니다");
        	 }
         }else if (sql.toLowerCase().startsWith("desc")) {
            tableModel.setRowCount(0);
            tableModel.setColumnCount(0);

            tableModel.addColumn("Column Name");
            tableModel.addColumn("Data Type");
            tableModel.addColumn("Nullable");

            String tableName = sql.substring(4).trim();

            try (Connection conn1 = getConn()) {
               DatabaseMetaData dbMetaData = conn1.getMetaData();

               ResultSet rsColumns = dbMetaData.getColumns(null, null, tableName.toUpperCase(), null);

               while (rsColumns.next()) {
            	   
                  String columnName = rsColumns.getString("COLUMN_NAME");

                  String dataType = rsColumns.getString("TYPE_NAME");

                  String nullable = rsColumns.getString("IS_NULLABLE");

                  tableModel.addRow(new Object[] { columnName, dataType, nullable });

               }

               showMessage("쿼리 실행성공");

            } catch (SQLException e) {
               handleSQLException(e);
            }
         }

         else if (sql.toLowerCase().startsWith("call")) {
            cstmt = conn.prepareCall(sql);

            boolean result = cstmt.execute();
            if (!result) {
               showMessage("쿼리 실행 성공");
            } else {
               // ResultSet이 반환되면 해당 처리도 필요할 수 있습니다.
               rs = cstmt.getResultSet();
               // rs에서 결과를 처리하는 부분 추가
               showMessage("쿼리 실행 성공, 결과 있음");
            }
         } else if (sql.toLowerCase().contains("declare") || sql.toLowerCase().contains("begin")
               || sql.toLowerCase().contains("end")) {

            cstmt = conn.prepareCall(sql);

            boolean result = cstmt.execute();

         } else {
            stmt = conn.prepareStatement(sql);
            int result = stmt.executeUpdate();
            if (result > 0) {
               showMessage("쿼리 실행 성공, " + result + "개의 행이 처리됨");
            } else {
               showMessage("쿼리 실행 성공, 변경된 행 없음");
            }

         }
      } catch (SQLException e) {
         handleSQLException(e);
      } finally {
         try {
            if (rs != null)
               rs.close();
            if (stmt != null)
               stmt.close();
         } catch (SQLException e) {
            showMessage("리소스 해제 오류: " + e.getMessage());
         }
      }
   }

   private void exportToExcel() {
      try (Workbook workbook = new XSSFWorkbook()) {
         Sheet sheet = workbook.createSheet("Query Results");

         // Creating header row
         Row headerRow = sheet.createRow(0);
         for (int col = 0; col < tableModel.getColumnCount(); col++) {
            headerRow.createCell(col).setCellValue(tableModel.getColumnName(col));
         }

         // Filling in the data
         for (int row = 0; row < tableModel.getRowCount(); row++) {
            Row dataRow = sheet.createRow(row + 1);
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
               dataRow.createCell(col).setCellValue(tableModel.getValueAt(row, col).toString());
            }
         }
         

         // Saving the Excel file
         JFileChooser fileChooser = new JFileChooser();
         fileChooser.setDialogTitle("Save Excel File");
         int userSelection = fileChooser.showSaveDialog(this);
         if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
               filePath += ".xlsx";
            }
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
               workbook.write(fileOut);
               showMessage("엑셀 파일로 저장되었습니다.");
            }
         }
      } catch (Exception e) {
         showMessage("엑셀 저장 중 오류가 발생했습니다: " + e.getMessage());
      }
   }

   // SQL 오류 처리 함수
   private void handleSQLException(SQLException e) {
      switch (e.getErrorCode()) {
      case 1:
         showMessage("기본 키 또는 유니크 제약 조건 위반");
         break;
      case 2291:
         showMessage("외래 키 제약 조건 위반");
         break;
      case 1400:
         showMessage("널 값을 허용하지 않는 컬럼에 널 값 삽입 불가");
         break;
      case 1722:
         showMessage("데이터 타입 불일치");
         break;
      case 933:
         showMessage("SQL 구문 오류");
         break;
      case 1031:
         showMessage("권한 부족");
         break;
      case 942:
         showMessage("테이블 또는 뷰가 존재하지 않음");
         break;
      case 911:
         showMessage("SQL 쿼리 내에 유효하지 않은 문자가 포함되어 있습니다.");
         break;
      case 2290:
         showMessage("제약 조건을 위배하여 데이터를 삽입할 수 없습니다.");
         break;
      case 2292:
         showMessage("외래 키 제약 조건을 위배하여 데이터를 삭제할 수 없습니다.");
         break;
      case 1403:
         showMessage("삭제하려는 데이터가 없습니다.");
         break;
      case 2241:
         showMessage("잘못된 권한 옵션입니다.");
         break;
      case 2287:
         showMessage("제약 조건을 위배하여 ALTER 명령어를 실행할 수 없습니다.");
         break;
      default:
         showMessage("SQL 오류: " + e.getMessage());
         System.out.println(e.getMessage());
         break;
      }
   }

   // 상태 메시지 출력 함수
   private void showMessage(String message) {
      messageLabel.setText(message);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == executeButton) {
         executeQuery();
      } else if (e.getSource() == modButton) {
         new ModGui(member);
      } else if (e.getSource() == exportButton) {
         exportToExcel(); // Handle export to Excel
      }
   }

}
