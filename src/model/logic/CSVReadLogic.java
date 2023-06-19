package model.logic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.beans.Monster;
import util.LimitConst;

public class CSVReadLogic {
	public List<Monster> convert(String path) {
		List<Monster> mlist = new ArrayList<>();
		try {
			FileReader in = new FileReader(path);
			BufferedReader br = new BufferedReader(in);
			AddMonsterLogic aml = new AddMonsterLogic();
			String line = br.readLine();
			//BufferReaderのreadLineは書き込む度に実行される。
//			なので下のwhileループの条件式の所に書き込んだりすると
//			ループの判定が行われる都度読み込み+改行が行われてしまう。

			while (line != null) {
				
				System.out.println(line);
				String[] parameters = line.split(",");
				int paramNum = parameters.length;

				if (paramNum != LimitConst.PARAM_LIMIT) {
					return null;
				}

				String _n = parameters[0];
				int _c = Integer.parseInt(parameters[1]);
				int _t = Integer.parseInt(parameters[2]);
				int _l = Integer.parseInt(parameters[3]);
				int _a = Integer.parseInt(parameters[4]);
				int _d = Integer.parseInt(parameters[5]);

				Monster m = new Monster(_n, _c, _t, _l, _a, _d);
				aml.execute(m, mlist);
				line = br.readLine();
			}
			br.close();
			in.close();

		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return mlist;
	}

}
