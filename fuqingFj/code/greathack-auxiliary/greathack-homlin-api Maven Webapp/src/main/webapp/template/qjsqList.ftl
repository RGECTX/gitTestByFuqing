<?xml version="1.0"?>
<?mso-application progid="Excel.Sheet"?>
<Workbook xmlns="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:o="urn:schemas-microsoft-com:office:office"
 xmlns:x="urn:schemas-microsoft-com:office:excel"
 xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882"
 xmlns:ss="urn:schemas-microsoft-com:office:spreadsheet"
 xmlns:html="http://www.w3.org/TR/REC-html40">
 <DocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <Title>PageTitle</Title>
  <Author>greathack</Author>
  <Keywords>Keywords</Keywords>
  <LastAuthor>greathack</LastAuthor>
  <Created>1996-12-17T01:32:42Z</Created>
  <LastSaved>2018-07-05T06:13:50Z</LastSaved>
  <Version>16.00</Version>
 </DocumentProperties>
 <CustomDocumentProperties xmlns="urn:schemas-microsoft-com:office:office">
  <KSOProductBuildVer dt:dt="string">2052-10.1.0.7400</KSOProductBuildVer>
 </CustomDocumentProperties>
 <OfficeDocumentSettings xmlns="urn:schemas-microsoft-com:office:office">
  <AllowPNG/>
 </OfficeDocumentSettings>
 <ExcelWorkbook xmlns="urn:schemas-microsoft-com:office:excel">
  <WindowHeight>9930</WindowHeight>
  <WindowWidth>21495</WindowWidth>
  <WindowTopX>32760</WindowTopX>
  <WindowTopY>32760</WindowTopY>
  <ProtectStructure>False</ProtectStructure>
  <ProtectWindows>False</ProtectWindows>
  <DisplayInkNotes>False</DisplayInkNotes>
 </ExcelWorkbook>
 <Styles>
  <Style ss:ID="Default" ss:Name="Normal">
   <Alignment ss:Vertical="Bottom"/>
   <Borders/>
   <Font ss:FontName="宋体" x:CharSet="134" ss:Size="12"/>
   <Interior/>
   <NumberFormat/>
   <Protection/>
  </Style>
 </Styles>
 <Worksheet ss:Name="Sheet1">
  <Table ss:ExpandedColumnCount="11" ss:ExpandedRowCount="${recordCount?if_exists}" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
   <Column ss:Index="3" ss:AutoFitWidth="0" ss:Width="63.75" ss:Span="1"/>
   <Column ss:Index="5" ss:AutoFitWidth="0" ss:Width="63" ss:Span="1"/>
   <Column ss:Index="7" ss:AutoFitWidth="0" ss:Width="102.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="123.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="117.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="66.75"/>
   <Column ss:AutoFitWidth="0" ss:Width="94.5"/>
   <Row>
    <Cell><Data ss:Type="String">编号</Data></Cell>
    <Cell><Data ss:Type="String">姓名</Data></Cell>
    <Cell><Data ss:Type="String">出生年月</Data></Cell>
    <Cell><Data ss:Type="String">性别</Data></Cell>
    <Cell><Data ss:Type="String">单位</Data></Cell>
    <Cell><Data ss:Type="String">职级</Data></Cell>
    <Cell><Data ss:Type="String">请（休）假天数</Data></Cell>
    <Cell><Data ss:Type="String">请（休）假开始时间</Data></Cell>
    <Cell><Data ss:Type="String">请（休）假结束时间</Data></Cell>
    <Cell><Data ss:Type="String">请假类型</Data></Cell>
    <Cell><Data ss:Type="String">请假事由及去处</Data></Cell>
   </Row>
   <#list qjsqList as item>
   <#assign qjsq=item>
   <Row ss:AutoFitHeight="0">
    <Cell><Data ss:Type="String">${qjsq.no?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.username?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.birthday?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.sex?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.orgName?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.position?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.daysOfLeave?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.beginDate?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.endDate?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.leaveTypeName?if_exists}</Data></Cell>
    <Cell><Data ss:Type="String">${qjsq.leaveReason?if_exists}</Data></Cell>
   </Row>
   </#list>
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <Print>
    <ValidPrinterInfo/>
    <PaperSizeIndex>9</PaperSizeIndex>
    <HorizontalResolution>600</HorizontalResolution>
    <VerticalResolution>0</VerticalResolution>
   </Print>
   <PageBreakZoom>60</PageBreakZoom>
   <Selected/>
   <Panes>
    <Pane>
     <Number>3</Number>
     <ActiveRow>1</ActiveRow>
     <ActiveCol>10</ActiveCol>
    </Pane>
   </Panes>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet2">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageBreakZoom>60</PageBreakZoom>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
 <Worksheet ss:Name="Sheet3">
  <Table ss:ExpandedColumnCount="1" ss:ExpandedRowCount="1" x:FullColumns="1"
   x:FullRows="1" ss:DefaultColumnWidth="54" ss:DefaultRowHeight="14.25">
  </Table>
  <WorksheetOptions xmlns="urn:schemas-microsoft-com:office:excel">
   <PageBreakZoom>60</PageBreakZoom>
   <ProtectObjects>False</ProtectObjects>
   <ProtectScenarios>False</ProtectScenarios>
  </WorksheetOptions>
 </Worksheet>
</Workbook>
