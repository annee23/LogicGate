#include <iostream>
#include <vector>
#include<string>
#include <queue>
using namespace std;
int main() {
	string input;
	cin >> input;

	int parity = 0;
	if (input.size() == 1)
		parity = 2;
	else if (input.size() <= 4)
		parity = 3;
	else 
		parity = 4;

	queue<int> q;
	for (auto a : input)
		q.push(a-'0');

	vector<int> v;
	v.push_back(-1);
	for (int i = 1; i <=input.size()+parity; i++) {
		if (i == 1 || i == 2 || i == 4 || i == 8 || i == 16 || i == 32)
			v.push_back(0);
		else
		{
			v.push_back(q.front());
			q.pop();
		}
	}

	int count1[17] = { 0, };
	for (int i = 1;i< v.size(); i*=2)
	{
		for (int j = i; j < v.size();) {
			for (int k = 0; k < i; k++)
			{
				if (j+k<v.size()&&v[j + k]==1)
					count1[i]++;
			}
			j += i * 2;
		}
	}
	for (int i = 1; i < v.size(); i*=2)
	{
		if (count1[i] % 2 == 1)
			count1[i] = 1;
		else
			count1[i] = 0;
	}
	for (int i = 1; i < v.size(); i*=2)
		v[i] = count1[i];

	for (int i = 1; i < v.size(); i++)
		cout << v[i];
	cout << endl;
	return 0;
}