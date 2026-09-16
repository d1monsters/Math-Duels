import requests
import json
import re
from bs4 import BeautifulSoup


class SdamGiaParser:
    def __init__(self, base_url, themes, exam, output_file):
        self.base_url = base_url
        self.themes = themes
        self.exam = exam
        self.output_file = output_file
        self.data = []

    def alt_to_latex(self, alt):
        s = alt.replace('\xad', '').strip()

        s = s.replace(r'\left', '').replace(r'\right', '')
        s = s.replace('д робь', 'дробь')
        s = s.replace(r'\tfrac', r'\frac')

        s = re.sub(r'корень\s+(\d+)\s+степени\s+из:?\s*начало аргумента:\s*(.+?)\s*конец аргумента',
                   r'\\sqrt[\1]{\2}', s)

        s = re.sub(r'логарифм\s+по\s+основанию\s+\(([^)]+)\)\s*\}?\s*\(([^)]+)\)',
                   r'\\log_{\1} (\2)', s)
        s = re.sub(r'логарифм\s+по\s+основанию\s+\(([^)]+)\)\s*\}?\s*(\d+)',
                   r'\\log_{\1} \2', s)
        s = re.sub(r'логарифм\s+по\s+основанию\s+(\S+?)\s*\(([^)]+)\)',
                   r'\\log_{\1} (\2)', s)
        s = re.sub(r'логарифм\s+по\s+основанию\s+(\S+?)\s+(\d+)',
                   r'\\log_{\1} \2', s)
        s = re.sub(r'\\log_\{ ([^}]+) \}', r'\\log_{\1}', s)

        s = re.sub(r'целая часть:\s*([^,]+),\s*дробная часть:\s*числитель:\s*([^,]+),\s*знаменатель:\s*([^\s]+)',
                   r'\1\\frac{\2}{\3}', s)

        s = re.sub(r'(?:квадратный\s+)?корень(?:\s+из)?:?\s*начало аргумента:\s*(.+?)\s*конец аргумента',
                   r'\\sqrt{\1}', s)

        s = re.sub(r'дробь:\s*числитель:\s*(.+?),\s*знаменатель:\s*(.+?)\s*конец дроби',
                   r'\\frac{\1}{\2}', s)

        s = re.sub(r'в степени\s+левая круглая скобка\s*(.*?)\s*правая круглая скобка',
                   r'^{(\1)}', s)
        s = re.sub(r'в степени\s*([^\s]+)', r'^{\1}', s)
        s = s.replace('в квадрате', '^{2}')
        s = s.replace('в кубе', '^{3}')

        s = s.replace('котангенс', r'\ctg')
        s = s.replace('тангенс', r'\tg')
        s = s.replace('косинус', r'\cos')
        s = s.replace('синус', r'\sin')

        s = s.replace('левая квадратная скобка', '[')
        s = s.replace('правая квадратная скобка', ']')
        s = s.replace('левая фигурная скобка', r'\{')
        s = s.replace('правая фигурная скобка', r'\}')
        s = s.replace('левая круглая скобка', '(')
        s = s.replace('правая круглая скобка', ')')

        s = s.replace('равносильно', r'\iff')
        s = s.replace('не равно', r'\neq')
        s = s.replace('меньше или равно', r'\leq')
        s = s.replace('больше или равно', r'\geq')
        s = s.replace('не принадлежит', r'\notin')
        s = s.replace('меньше', r'<')
        s = s.replace('больше', r'>')
        s = s.replace('принадлежит', r'\in')
        s = s.replace('подмножество', r'\subset')
        s = s.replace('объединение', r'\cup')
        s = s.replace('пересечение', r'\cap')
        s = s.replace('плюс', '+')
        s = s.replace('минус', '-')
        s = s.replace('умножить на', r'\cdot')

        s = re.sub(r'\\vec\s*([a-zA-Z])', r'\\vec{\1}', s)

        s = s.replace('Пи', r'\pi')
        s = s.replace('пи', r'\pi')
        s = s.replace('альфа', r'\alpha')
        s = s.replace('бета', r'\beta')
        s = s.replace('гамма', r'\gamma')

        s = s.replace('градусовC', r'^{\circ}C')
        s = s.replace('градусов', r'^{\circ}')

        s = re.sub(r'\\log_\{([^}]+)\}\s*\}', r'\\log_{\1}', s)
        s = re.sub(r'\\log _([0-9a-zA-Z]+)\s*\}', r'\\log_{\1}', s)
        s = re.sub(r'\\log \}_([0-9a-zA-Z]+)\s*\}', r'\\log_{\1}', s)

        s = re.sub(r'\s+', ' ', s).strip()
        return s

    def parse(self):
        for theme in self.themes:
            n = 1
            while True:
                url = f"{self.base_url}?theme={theme}&page={n}"
                try:
                    response = requests.get(url, timeout=15)
                except requests.exceptions.RequestException as e:
                    print(f"пропуск {url}: {e}")
                    break

                soup = BeautifulSoup(response.text, 'html.parser')
                problems = soup.find_all('div', class_="prob_maindiv")

                if not problems:
                    break

                for i in problems:
                    item = {"exam": self.exam, "theme": theme, "condition": "", "answer": ""}

                    pbody = i.find_all('div', class_='pbody')
                    if pbody:
                        body = pbody[0]
                        for img in body.find_all('img'):
                            alt = img.get('alt', '')
                            if alt:
                                latex = self.alt_to_latex(alt)
                                img.replace_with(f" ${latex}$ ")
                        item["condition"] = body.get_text().replace('\xad', '')

                    answer = i.find('div', class_='answer')
                    if answer:
                        item["answer"] = answer.get_text().replace('\xad', '').replace('Ответ: ', '')

                    self.data.append(item)

                n += 1

        self.save()
        return self.data

    def save(self):
        with open(self.output_file, "w", encoding="utf-8") as f:
            json.dump(self.data, f, ensure_ascii=False, indent=2)
        print(f"{self.output_file}: {len(self.data)} задач")


if __name__ == "__main__":
    parsers = [
        SdamGiaParser(
            "https://ege.sdamgia.ru/test",
            [166, 185, 265, 88, 84, 85, 86, 87, 89, 334, 217, 210, 79, 90, 96, 102, 94, 111, 112, 113, 114, 182, 192, 193, 194, 197, 14, 9, 10, 11, 12, 13, 55, 60, 57, 62, 61, 65],
            "ege",
            "problems_ege.json",
        ),
        SdamGiaParser(
            "https://oge.sdamgia.ru/test",
            [20, 69, 113, 46, 15, 79, 22, 76, 77, 82, 24, 80, 81, 6, 59, 129, 54, 7, 43, 71, 27, 32, 34, 11, 12, 35, 38, 36, 33, 42, 13, 37],
            "oge",
            "problems_oge.json",
        ),
        SdamGiaParser(
            "https://math10-vpr.sdamgia.ru/test",
            [1, 4, 12, 6, 29, 7, 50, 17, 33, 2, 18, 20, 19, 3, 21, 10, 13, 38, 14, 41, ],
            "vpr10",
            "problems_vpr10.json",
        ),
        SdamGiaParser(
            "https://math8-vpr.sdamgia.ru/test",
            [67, 18, 46, 88, 10, 92, 93, 1, 20, 2, 89, 95, 9, 96, 94, ],
            "vpr8",
            "problems_vpr8.json",
        ),
        SdamGiaParser(
            "https://math7-vpr.sdamgia.ru/test",
            [4, 6, 5, 51, 49, 9, 55],
            "vpr7",
            "problems_vpr7.json",
        ),
        SdamGiaParser(
            "https://math6-vpr.sdamgia.ru/test",
            [16, 5, 32, 36, 12, 38, 41, 40, 2, 3, 4, 6, 9, 35, 11],
            "vpr6",
            "problems_vpr6.json",
        ),
    ]

    for parser in parsers:
        parser.parse()