package project;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JOptionPane;

public class SelectDialogEvt extends WindowAdapter implements ActionListener {

	private SelectDialog sd;// has a

	private FileDialog fdView;
	private FileDialog fdReport;

	private List<String> logLine;
	private int allCodeCnt; // logLine 전체 low수

	public SelectDialogEvt(SelectDialog sd) {// has a
		this.sd = sd;
	}// SelectDialogEvt

	@Override
	public void windowClosing(WindowEvent we) {
		sd.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == sd.getJbtnView()) {
			try {
				if (sd.getJtfFirstLine().getText().isEmpty() || sd.getJtfLastLine().getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "라인을 입력해주세요^^;");
				} else if (Integer.valueOf(sd.getJtfFirstLine().getText()) >= Integer
						.valueOf(sd.getJtfLastLine().getText())) {// 첫번째 라인값이 마지막 라인값과 동일하거나 클 경우
					JOptionPane.showMessageDialog(null, "첫번째 라인값이 마지막 라인값과 동일하거나 큽니다. \n재입력 바랍니다.", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				} else {
					viewBtn();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException npe) {

//			} catch (NumberFormatException nfe) {

			}
		} // end if

		if (ae.getSource() == sd.getJbtnReport()) {
			String slId = Login.jtfId.getText();
			String sdFirstlLine = sd.getJtfFirstLine().getText();
			String sdLastLine = sd.getJtfLastLine().getText();
			try {
				if (slId.trim().equals("root")) {// root계정시 실행 불가.
					JOptionPane.showMessageDialog(null, "root계정은 사용불가(~,.~)");
				} else {// root이면 실행.
					if (sdFirstlLine.isEmpty() || sdLastLine.isEmpty()) { ////// 라인 수 입력란이 비어있을 때 출력
						JOptionPane.showMessageDialog(null, "라인을 입력한 후 VIEW버튼을 눌러주세요");
					} else {
						mkdir();
						reportSave();
						setFileName();
					} // end else

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(null, "VIEW버튼먼저 Click~!~!");
			} // catch

		} // end if

	}// actionPerformed

	public void mkdir() {
		File file = new File("E:\\project\\report"); // project안에 report폴더 생성
		if (!file.exists()) {
			file.mkdirs();
		}
	}// mkdir

	public void viewBtn() throws IOException {
		fdView = new FileDialog(sd, "파일 열기", FileDialog.LOAD);
		fdView.setDirectory("C:\\project"); // 열었을 때 파일 경로 설정.
		fdView.setVisible(true);

		// 디렉토리명, 파일명 얻기
		String path = fdView.getDirectory();
		String name = fdView.getFile();

		if (name != null) {
			sd.setTitle(path + name);
			new ViewPrint(sd, this); // 네임값을 받을시 즉 클릭시에만 열리게 설정
		} // end if
	}// viewBtn

	public void reportSave() throws IOException {
		String path = fdView.getDirectory();
		String name = fdView.getFile();
		File file = new File("E:\\project\\report\\abc.txt");

		// 0. 파일이 존재하면 덮어쓸 것인가를 물어본다.
		// 파일이 없다면 파일을 생성하고 파일이 있다면 덮어 쓸것인지를 물어본 후
		// ConfirmDialog로 물어본 후 예라면 덮어쓰고 아니오라면 파일을 덮어쓰지 않도록
		boolean createFlag = false;
		if (file.exists()) {
			int select = JOptionPane.showConfirmDialog(null, " 이미 파일이 존재합니다 덮겠습니까?", "선택하세요",
					JOptionPane.YES_NO_OPTION);

			switch (select) {
			case JOptionPane.OK_OPTION:
				createFlag = false;
				break;
			case JOptionPane.NO_OPTION:
				createFlag = true;
			}// end switch
		} else {
		}

		BufferedWriter bw = null;
		try {
			// 1. 스트림 생성 : 없으면 만들고, 있으면 덮어씀
			if (!createFlag) {
				bw = new BufferedWriter(new FileWriter(file));
				// 생성 또는 덮어 씀
				String resultMsg = ViewPrint.jlblResult.getText();
				String msg = ViewPrint.jtaView.getText();

				bw.write(resultMsg + msg);

				bw.flush();

				JOptionPane.showMessageDialog(null, "저장했습니다.");
				JOptionPane.showMessageDialog(null, "다른 결과를 얻으시려면 라인값을  재입력후 \nVIEW버튼을 누른 후 다시 실행해주세요.", "안내",
						JOptionPane.INFORMATION_MESSAGE);

			} // end if

		} finally {

			if (bw != null) {
				// 스트림에 남아있는 내용을 목적지로 분출(flush)하고 연결을 끊는다.
				bw.close();
			} // end if
		} // end finally

	} // UseFileOutputStream

	public void setFileName() {
		// 이름을 변경시킬 파일을 포함한 디렉토리 경로 가져오기
		File file = new File("E:\\project\\report");

		// 하위 파일 가져오기
		File[] sublist = file.listFiles();

		// 파일별 처리
		for (int i = 0; i < sublist.length; i++) {
			// 파일 속성을 가져오기 위한 class
			BasicFileAttributes attr = null;

			// 파일 이름을 가져와서 확장자 설정
			String filename = sublist[i].getName();
			String fExtension = ".dat"; // 확장자

			if (filename.lastIndexOf(",") != -1) {
				fExtension = filename.substring(filename.lastIndexOf("."));
			} // end if

			try {
				// 파일 속성 가져온다.
				attr = Files.readAttributes(sublist[i].toPath(), BasicFileAttributes.class);

				// 파일의 생성시간 , 수정시간 가져옴
				FileTime crtFTime = attr.creationTime();
				FileTime mdfFTime = attr.lastModifiedTime();

				// date format을 설정
				SimpleDateFormat dataform = new SimpleDateFormat("yyyyMMdd_HHmmss");
				// 이걸로 파일명을 날짜별로 나오게 사용도 가능
				String crtDTime = dataform.format(new Date(crtFTime.toMillis()));
				String mdfDTime = dataform.format(new Date(mdfFTime.toMillis()));

				// 파일 이름을 디폴드 값으로 설정
				String fileDate = crtDTime;

				/* 수정한 날짜가 만든 날짜가 뒤바뀐 경우 처리 */
				if (Integer.parseInt(crtDTime.substring(0, 8)) > Integer.parseInt(mdfDTime.substring(0, 8))) {
					fileDate = mdfDTime;
				}

				// 파일 이름 변경
//				sublist[i].renameTo(new File(sublist[i].getParent() + "\\" + "report_" + fileDate+ fExtension ));
				sublist[i].renameTo(
						new File(sublist[i].getParent() + "\\" + "report_" + crtFTime.toMillis() + fExtension));

			} catch (IOException e) {
				e.printStackTrace();
			} // end catch

		} // end for

	}


//	1. 최다사용 키의 이름과 횟수 | java xx회
	public String maxUsedKey() throws IOException {
		logLine = new ArrayList<String>();
		List<String> keyLine = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> maxMap = new HashMap<String, Integer>();

		File file = new File(sd.getTitle());///////////////////////// viewBtn에서 설정한 경로를 타이틀로 옮겨 뷰프린트 타이틀로 옮겨 super() 안의
											///////////////////////// 그 타이틀 값을 받아옴

		if (file.exists()) {
			BufferedReader br = null;
			try {
				// 2. Stream을 사용한 연결
				br = new BufferedReader(new FileReader(file));
				// 3. 줄 단위로 읽기
				String data = "";
				String key = "";
				allCodeCnt = 0;
				while ((data = br.readLine()) != null) { // \n전까지 읽어 들인다.
					allCodeCnt++;
					logLine.add(data);// 1986까지 들어가있는 김성태
					if (data.contains("key")) {
						key = data.substring(data.indexOf("=") + 1, data.indexOf("&"));
						keyLine.add(key);
					} // end if
				} // end while

			} // try
			finally {
				if (br != null) {
					// 4. 연결끊기
					br.close();
				} // end if
			} // finally

		} else {
			JOptionPane.showMessageDialog(null, file + "");
		} // end if

		int num = 0;
		for (String str : keyLine) {
			Integer count = map.get(str);
			if (count == null) {
				map.put(str, 1);
			} else {
				map.put(str, count + 1);
			} // end else
			num++;
		} // end for

		double proportion = (double) num;
		for (String opp : map.keySet()) {
			maxMap.put(opp, map.get(opp));
		} // end for

		Entry<String, Integer> maxEntry = null;

		Set<Entry<String, Integer>> entrySet = maxMap.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			} // end if
		} // end for

		return maxEntry.getKey() + "   " + maxEntry.getValue() + "회";
	}// maxUsedKey

//	2. 브라우저별 접속횟수, 비율
//		IE - xx (xx%)
//		Chrome - xx (xx%)
	public String connectBrowser() {

		List<String> browsLine = new ArrayList<String>();// brow 다 읽기 - > substring해야함.
		int startidx = 0;// []인덱스 얻기
		int endidx = 0;
		String browser = ""; // 브라우저 라인 값 확인하려고 만듦
		// browsLine for문 돌리기
		for (String data : logLine) {
			// 인덱스
			startidx = data.indexOf("][", 6);
			endidx = data.indexOf("][", startidx + 1);
			browser = data.substring(startidx + 2, endidx);
			browsLine.add(browser);
//			System.out.println(browser);
		} // end for

		Map<String, Integer> map = new HashMap<String, Integer>();
		Integer count = 0;
		for (String str : browsLine) {
			count = map.get(str);
			if (count == null) {
				map.put(str, 1);
			} else {
				map.put(str, count + 1);
			} // end else
		} // end for

		// map에 들어있는 키, 값 얻기
		String result = "";
		String proportion = "";
		double pp = 0;

		for (Entry<String, Integer> a : map.entrySet()) {
			String key = a.getKey();
			Integer value = a.getValue();
			pp = ((double) value / allCodeCnt) * 100;
			proportion = String.format("%.2f", pp);
			result += "    " + key + " - " + value + "회,(" + proportion + "%)" + "\n\t";
		} // end for

		return result;

	}// connectBrowser

//	3. 서비스를 성공적으로 수행한(200) 횟수,실패(404) 횟수
	public String servicePerform() {
		String succode = "200";
		String failcode = "404";
		int sucCnt = 0;
		int failCnt = 0;
		for (String data : logLine) {
			// 인덱스
			if (data.contains(succode)) {
				sucCnt++;
			} else if (data.contains(failcode)) {
				failCnt++;
			}
		} // end for
		return "서비스 성공(200)횟수 : " + sucCnt + "회 ,실패(404) 횟수 : " + failCnt + "회";

	}// servicePerform

//	4. 요청이 가장 많은 시간 [10시]
	public String mostRequestTime() {
		List<String> timeLine = new ArrayList<String>();
		String time = ""; // 브라우저 라인 값

		for (String data : logLine) {
			// 인덱스
			time = data.substring(data.indexOf(" ") + 1, data.indexOf(" ") + 3);
			timeLine.add(time);
		} // end for

		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String str : timeLine) {
			Integer count = map.get(str);

			if (count == null) {
				map.put(str, 1);
			} else {
				map.put(str, count + 1);
			} // end else

		} // end for

		Entry<String, Integer> maxEntry = null;

		Set<Entry<String, Integer>> entrySet = map.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			} // end if
		} // end for

		return "요청이 가장 많은 시간 : " + maxEntry.getKey() + "시";
	}// mostRequestTime

//	5. 비정상적인 요청(403)이 발생한 횟수,비율구하기
	public String unNormalRequest() {
		String errcode = "403";
		boolean flag = false;
		int errcnt = 0;
		for (String data : logLine) {
			// 인덱스
			flag = data.contains(errcode);
			if (flag) {
				errcnt++;
			}
		} // end for

		// 비율
		double pp = (errcnt / (double) allCodeCnt) * 100;

		String proportion = String.format("%.2f", pp);

		return "비정상적요청(403)발생 횟수 : " + errcnt + "회, 비율 : " + proportion + "%";
	}// unNormalRequest

//	6. 요청에 대한 에러(500)가 발생한 횟수, 비율 구하기
	public String requestError() {
		String errcode = "500";
		boolean flag = false;
		int errcnt = 0;
		for (String data : logLine) {
			// 인덱스
			flag = data.contains(errcode);
			if (flag) {
				errcnt++;
			}
		} // end for

		double pp = (errcnt / (double) allCodeCnt) * 100;

		String proportion = String.format("%.2f", pp);

		return "비정상적요청(505) 발생한 횟수 : " + errcnt + "회, 비율 : " + proportion + "%";
	}// requestError

//	7. 입력되는 라인에 해당하는 정보출력
//	(예 :1000~1500라인 이 입력되면)
	public String maxUsedValue() throws IOException {
		logLine = new ArrayList<String>();
		List<String> keyLine = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> maxMap = new HashMap<String, Integer>();

		File file = new File(sd.getTitle());

		if (file.exists()) {
			BufferedReader br = null;
			try {
				// 2. Stream을 사용한 연결
				br = new BufferedReader(new FileReader(file));
				// 3. 줄 단위로 읽기
				String data = "";
				String key = "";

				if (allCodeCnt > Integer.valueOf(sd.getJtfLastLine().getText()) - 1) { // allCodeCnt 넘어가는 lastLine값
																						// 받았을때.
					for (int i = (Integer.valueOf(sd.getJtfFirstLine().getText()) - 1); i < Integer
							.valueOf(sd.getJtfLastLine().getText()) - 1; i++) {
						logLine.add(data = br.readLine());// 1986까지 들어가있는 김성태
						if (data.contains("key")) {
							key = data.substring(data.indexOf("=") + 1, data.indexOf("&"));
							keyLine.add(key);
						} // end if
					} // end if
				} else {
					JOptionPane.showMessageDialog(null, "최대값 " + (allCodeCnt) + "까지 입력가능ㅋ");
					// 인덱스는 0~1986까지 저장되어있고 줄은 1~1987까지라서 설정해야함
				}
			} // try
			finally {
				if (br != null) {
					// 4. 연결끊기
					br.close();
				} // end if
			} // finally

		} else {
			JOptionPane.showMessageDialog(null, file + "");
		} // end if

		int num = 0;
		for (String str : keyLine) {
			Integer count = map.get(str);
			if (count == null) {
				map.put(str, 1);
			} else {
				map.put(str, count + 1);
			} // end else
			num++;
		} // end for

		double proportion = (double) num;
		for (String opp : map.keySet()) {
			maxMap.put(opp, map.get(opp));
		} // end for

		Entry<String, Integer> maxEntry = null;

		Set<Entry<String, Integer>> entrySet = maxMap.entrySet();
		for (Entry<String, Integer> entry : entrySet) {
			if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
				maxEntry = entry;
			} // end if
		} // end for

		return maxEntry.getKey() + "   " + maxEntry.getValue() + "회";
	}// maxUsedValue

}// SelectDialogEvt
