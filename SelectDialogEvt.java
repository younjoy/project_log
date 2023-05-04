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
	private int allCodeCnt; // logLine ��ü low��

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
					JOptionPane.showMessageDialog(null, "������ �Է����ּ���^^;");
				} else if (Integer.valueOf(sd.getJtfFirstLine().getText()) >= Integer
						.valueOf(sd.getJtfLastLine().getText())) {// ù��° ���ΰ��� ������ ���ΰ��� �����ϰų� Ŭ ���
					JOptionPane.showMessageDialog(null, "ù��° ���ΰ��� ������ ���ΰ��� �����ϰų� Ů�ϴ�. \n���Է� �ٶ��ϴ�.", "ERROR",
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
				if (slId.trim().equals("root")) {// root������ ���� �Ұ�.
					JOptionPane.showMessageDialog(null, "root������ ���Ұ�(~,.~)");
				} else {// root�̸� ����.
					if (sdFirstlLine.isEmpty() || sdLastLine.isEmpty()) { ////// ���� �� �Է¶��� ������� �� ���
						JOptionPane.showMessageDialog(null, "������ �Է��� �� VIEW��ư�� �����ּ���");
					} else {
						mkdir();
						reportSave();
						setFileName();
					} // end else

				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException npe) {
				JOptionPane.showMessageDialog(null, "VIEW��ư���� Click~!~!");
			} // catch

		} // end if

	}// actionPerformed

	public void mkdir() {
		File file = new File("E:\\project\\report"); // project�ȿ� report���� ����
		if (!file.exists()) {
			file.mkdirs();
		}
	}// mkdir

	public void viewBtn() throws IOException {
		fdView = new FileDialog(sd, "���� ����", FileDialog.LOAD);
		fdView.setDirectory("C:\\project"); // ������ �� ���� ��� ����.
		fdView.setVisible(true);

		// ���丮��, ���ϸ� ���
		String path = fdView.getDirectory();
		String name = fdView.getFile();

		if (name != null) {
			sd.setTitle(path + name);
			new ViewPrint(sd, this); // ���Ӱ��� ������ �� Ŭ���ÿ��� ������ ����
		} // end if
	}// viewBtn

	public void reportSave() throws IOException {
		String path = fdView.getDirectory();
		String name = fdView.getFile();
		File file = new File("E:\\project\\report\\abc.txt");

		// 0. ������ �����ϸ� ��� ���ΰ��� �����.
		// ������ ���ٸ� ������ �����ϰ� ������ �ִٸ� ���� ���������� ��� ��
		// ConfirmDialog�� ��� �� ����� ����� �ƴϿ���� ������ ����� �ʵ���
		boolean createFlag = false;
		if (file.exists()) {
			int select = JOptionPane.showConfirmDialog(null, " �̹� ������ �����մϴ� ���ڽ��ϱ�?", "�����ϼ���",
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
			// 1. ��Ʈ�� ���� : ������ �����, ������ ���
			if (!createFlag) {
				bw = new BufferedWriter(new FileWriter(file));
				// ���� �Ǵ� ���� ��
				String resultMsg = ViewPrint.jlblResult.getText();
				String msg = ViewPrint.jtaView.getText();

				bw.write(resultMsg + msg);

				bw.flush();

				JOptionPane.showMessageDialog(null, "�����߽��ϴ�.");
				JOptionPane.showMessageDialog(null, "�ٸ� ����� �����÷��� ���ΰ���  ���Է��� \nVIEW��ư�� ���� �� �ٽ� �������ּ���.", "�ȳ�",
						JOptionPane.INFORMATION_MESSAGE);

			} // end if

		} finally {

			if (bw != null) {
				// ��Ʈ���� �����ִ� ������ �������� ����(flush)�ϰ� ������ ���´�.
				bw.close();
			} // end if
		} // end finally

	} // UseFileOutputStream

	public void setFileName() {
		// �̸��� �����ų ������ ������ ���丮 ��� ��������
		File file = new File("E:\\project\\report");

		// ���� ���� ��������
		File[] sublist = file.listFiles();

		// ���Ϻ� ó��
		for (int i = 0; i < sublist.length; i++) {
			// ���� �Ӽ��� �������� ���� class
			BasicFileAttributes attr = null;

			// ���� �̸��� �����ͼ� Ȯ���� ����
			String filename = sublist[i].getName();
			String fExtension = ".dat"; // Ȯ����

			if (filename.lastIndexOf(",") != -1) {
				fExtension = filename.substring(filename.lastIndexOf("."));
			} // end if

			try {
				// ���� �Ӽ� �����´�.
				attr = Files.readAttributes(sublist[i].toPath(), BasicFileAttributes.class);

				// ������ �����ð� , �����ð� ������
				FileTime crtFTime = attr.creationTime();
				FileTime mdfFTime = attr.lastModifiedTime();

				// date format�� ����
				SimpleDateFormat dataform = new SimpleDateFormat("yyyyMMdd_HHmmss");
				// �̰ɷ� ���ϸ��� ��¥���� ������ ��뵵 ����
				String crtDTime = dataform.format(new Date(crtFTime.toMillis()));
				String mdfDTime = dataform.format(new Date(mdfFTime.toMillis()));

				// ���� �̸��� ������ ������ ����
				String fileDate = crtDTime;

				/* ������ ��¥�� ���� ��¥�� �ڹٲ� ��� ó�� */
				if (Integer.parseInt(crtDTime.substring(0, 8)) > Integer.parseInt(mdfDTime.substring(0, 8))) {
					fileDate = mdfDTime;
				}

				// ���� �̸� ����
//				sublist[i].renameTo(new File(sublist[i].getParent() + "\\" + "report_" + fileDate+ fExtension ));
				sublist[i].renameTo(
						new File(sublist[i].getParent() + "\\" + "report_" + crtFTime.toMillis() + fExtension));

			} catch (IOException e) {
				e.printStackTrace();
			} // end catch

		} // end for

	}


//	1. �ִٻ�� Ű�� �̸��� Ƚ�� | java xxȸ
	public String maxUsedKey() throws IOException {
		logLine = new ArrayList<String>();
		List<String> keyLine = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> maxMap = new HashMap<String, Integer>();

		File file = new File(sd.getTitle());///////////////////////// viewBtn���� ������ ��θ� Ÿ��Ʋ�� �Ű� ������Ʈ Ÿ��Ʋ�� �Ű� super() ����
											///////////////////////// �� Ÿ��Ʋ ���� �޾ƿ�

		if (file.exists()) {
			BufferedReader br = null;
			try {
				// 2. Stream�� ����� ����
				br = new BufferedReader(new FileReader(file));
				// 3. �� ������ �б�
				String data = "";
				String key = "";
				allCodeCnt = 0;
				while ((data = br.readLine()) != null) { // \n������ �о� ���δ�.
					allCodeCnt++;
					logLine.add(data);// 1986���� ���ִ� �輺��
					if (data.contains("key")) {
						key = data.substring(data.indexOf("=") + 1, data.indexOf("&"));
						keyLine.add(key);
					} // end if
				} // end while

			} // try
			finally {
				if (br != null) {
					// 4. �������
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

		return maxEntry.getKey() + "   " + maxEntry.getValue() + "ȸ";
	}// maxUsedKey

//	2. �������� ����Ƚ��, ����
//		IE - xx (xx%)
//		Chrome - xx (xx%)
	public String connectBrowser() {

		List<String> browsLine = new ArrayList<String>();// brow �� �б� - > substring�ؾ���.
		int startidx = 0;// []�ε��� ���
		int endidx = 0;
		String browser = ""; // ������ ���� �� Ȯ���Ϸ��� ����
		// browsLine for�� ������
		for (String data : logLine) {
			// �ε���
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

		// map�� ����ִ� Ű, �� ���
		String result = "";
		String proportion = "";
		double pp = 0;

		for (Entry<String, Integer> a : map.entrySet()) {
			String key = a.getKey();
			Integer value = a.getValue();
			pp = ((double) value / allCodeCnt) * 100;
			proportion = String.format("%.2f", pp);
			result += "    " + key + " - " + value + "ȸ,(" + proportion + "%)" + "\n\t";
		} // end for

		return result;

	}// connectBrowser

//	3. ���񽺸� ���������� ������(200) Ƚ��,����(404) Ƚ��
	public String servicePerform() {
		String succode = "200";
		String failcode = "404";
		int sucCnt = 0;
		int failCnt = 0;
		for (String data : logLine) {
			// �ε���
			if (data.contains(succode)) {
				sucCnt++;
			} else if (data.contains(failcode)) {
				failCnt++;
			}
		} // end for
		return "���� ����(200)Ƚ�� : " + sucCnt + "ȸ ,����(404) Ƚ�� : " + failCnt + "ȸ";

	}// servicePerform

//	4. ��û�� ���� ���� �ð� [10��]
	public String mostRequestTime() {
		List<String> timeLine = new ArrayList<String>();
		String time = ""; // ������ ���� ��

		for (String data : logLine) {
			// �ε���
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

		return "��û�� ���� ���� �ð� : " + maxEntry.getKey() + "��";
	}// mostRequestTime

//	5. ���������� ��û(403)�� �߻��� Ƚ��,�������ϱ�
	public String unNormalRequest() {
		String errcode = "403";
		boolean flag = false;
		int errcnt = 0;
		for (String data : logLine) {
			// �ε���
			flag = data.contains(errcode);
			if (flag) {
				errcnt++;
			}
		} // end for

		// ����
		double pp = (errcnt / (double) allCodeCnt) * 100;

		String proportion = String.format("%.2f", pp);

		return "����������û(403)�߻� Ƚ�� : " + errcnt + "ȸ, ���� : " + proportion + "%";
	}// unNormalRequest

//	6. ��û�� ���� ����(500)�� �߻��� Ƚ��, ���� ���ϱ�
	public String requestError() {
		String errcode = "500";
		boolean flag = false;
		int errcnt = 0;
		for (String data : logLine) {
			// �ε���
			flag = data.contains(errcode);
			if (flag) {
				errcnt++;
			}
		} // end for

		double pp = (errcnt / (double) allCodeCnt) * 100;

		String proportion = String.format("%.2f", pp);

		return "����������û(505) �߻��� Ƚ�� : " + errcnt + "ȸ, ���� : " + proportion + "%";
	}// requestError

//	7. �ԷµǴ� ���ο� �ش��ϴ� �������
//	(�� :1000~1500���� �� �ԷµǸ�)
	public String maxUsedValue() throws IOException {
		logLine = new ArrayList<String>();
		List<String> keyLine = new ArrayList<String>();
		Map<String, Integer> map = new HashMap<String, Integer>();
		Map<String, Integer> maxMap = new HashMap<String, Integer>();

		File file = new File(sd.getTitle());

		if (file.exists()) {
			BufferedReader br = null;
			try {
				// 2. Stream�� ����� ����
				br = new BufferedReader(new FileReader(file));
				// 3. �� ������ �б�
				String data = "";
				String key = "";

				if (allCodeCnt > Integer.valueOf(sd.getJtfLastLine().getText()) - 1) { // allCodeCnt �Ѿ�� lastLine��
																						// �޾�����.
					for (int i = (Integer.valueOf(sd.getJtfFirstLine().getText()) - 1); i < Integer
							.valueOf(sd.getJtfLastLine().getText()) - 1; i++) {
						logLine.add(data = br.readLine());// 1986���� ���ִ� �輺��
						if (data.contains("key")) {
							key = data.substring(data.indexOf("=") + 1, data.indexOf("&"));
							keyLine.add(key);
						} // end if
					} // end if
				} else {
					JOptionPane.showMessageDialog(null, "�ִ밪 " + (allCodeCnt) + "���� �Է°��ɤ�");
					// �ε����� 0~1986���� ����Ǿ��ְ� ���� 1~1987������ �����ؾ���
				}
			} // try
			finally {
				if (br != null) {
					// 4. �������
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

		return maxEntry.getKey() + "   " + maxEntry.getValue() + "ȸ";
	}// maxUsedValue

}// SelectDialogEvt
