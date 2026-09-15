import requests
import json
from bs4 import BeautifulSoup

def main():
    sources = [
        ("https://ege.sdamgia.ru/test", [166, 185, 265, 88, 84, 85, 86, 87, 89, 334, 217, 210], "ege"),
        ("https://oge.sdamgia.ru/test", [20, 69, 113, 46, 15, 79, 22, 76, 77, 82, 24, 80, 81], "oge"),
        ("https://math10-vpr.sdamgia.ru/test", [1, 4, 12, 6, 29, 7, 50, 17, 33], "vpr10"),
        ("https://math8-vpr.sdamgia.ru/test", [67, 18, 46, 88, 10, 92, 93], "vpr8"),
        ("https://math7-vpr.sdamgia.ru/test", [4, 6, 5, 51, 49], "vpr7"),
        ("https://math6-vpr.sdamgia.ru/test", [16, 5, 32, 36, 12, 38, 41, 40, 2], "vpr6")
    ]

    data = []

    for base_url, themes, exam in sources:
        for theme in themes:
            n = 1
            while True:
                url = f"{base_url}?theme={theme}&page={n}"
                response = requests.get(url)
                soup = BeautifulSoup(response.text, 'html.parser')
                problems = soup.find_all('div', class_="prob_maindiv")

                if not problems:
                    break

                for i in problems:
                    item = {"exam": exam, "theme": theme, "condition": "", "answer": ""}

                    pbody = i.find_all('div', class_='pbody')
                    if pbody:
                        item["condition"] = pbody[0].get_text().replace('\xad', '')

                    answer = i.find('div', class_='answer')
                    if answer:
                        item["answer"] = answer.get_text().replace('\xad', '').replace('Ответ: ', '')

                    data.append(item)

                n += 1

    with open("problems.json", "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

    return data

if __name__ == "__main__":
    result = main()
    print(len(result))