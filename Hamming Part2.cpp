#include <iostream>
#include <vector>
#include<string>
#include <cmath>
using namespace std;
int main() {
	string input;
	cin >> input;

	vector<int>v;
	v.push_back(-1);
	for (auto a : input)
		v.push_back(a-'0');

	int parity = 0;
	if (input.size() == 3)
		parity = 2;
	else if (input.size() <= 7)
		parity = 3;
	else
		parity = 4;

	int count1[17] = { 0, };
	for (int i = 1; i < v.size(); i *= 2)
	{
		for (int j = i; j < v.size();) {
			for (int k = 0; k < i; k++)
			{
				if (j + k < v.size() && v[j + k] == 1)
					count1[i]++;
			}
			j += i * 2;
		}
	}

	bool isErr = false;
	vector<int>p;
	for (int i = 1; i < v.size(); i *= 2)
	{
		if (count1[i] % 2 == 1)
		{
			isErr = true;
			p.push_back(1);
		}
		else
			p.push_back(0);
	}

	if (!isErr)
		cout << "NO ERROR" << endl;
	else
	{
		int where = 0;
		for (int i = parity; i > 0; i--)
		{
			int temp = p.back();
			p.pop_back();
			where += pow(2, i-1) * temp;
		}
		cout << where << "번 위치에 에러 비트 발생_ 수정합니다" << endl;
		if (v[where] == 1)
			v[where] = 0;
		else
			v[where] = 1;
		for (int i = 1; i < v.size(); i++)
			cout << v[i];
		cout << endl;
	}
	return 0;
}