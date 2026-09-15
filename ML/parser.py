from playwright.sync_api import sync_playwright
from bs4 import BeautifulSoup

def main():
    with sync_playwright() as p:
        browser = p.firefox.launch()
        context = browser.new_context(ignore_https_errors=True)
        page = context.new_page()

        flag = True

        n = 1

        while flag == True:

            url = f"https://ege.sdamgia.ru/test?theme=166&page={n}"

            page.goto(url)

            html = page.content()
            
            soup = BeautifulSoup(html, 'html.parser')
            problems = soup.find_all('div', class_="prob_maindiv")

            for i in problems:
                pbody = i.find_all('div', class_='pbody')
                if pbody:
                    print(pbody[0].get_text())
                else:
                    flag = False

                answer = i.find('div', class_='answer')
                if answer:
                    print(answer.get_text())

                print('---')

            n+=1

        browser.close()
            
if __name__ == "__main__":
    main()