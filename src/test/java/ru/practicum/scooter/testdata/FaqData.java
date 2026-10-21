package ru.practicum.scooter.testdata;

import java.util.Arrays;
import java.util.List;

public final class FaqData {

    private FaqData() {
    }

    public static List<FaqItem> items() {
        return Arrays.asList(
                new FaqItem(
                        "Сколько это стоит? И как оплатить?",
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                new FaqItem(
                        "Хочу сразу несколько самокатов! Так можно?",
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, "
                                + "можете просто сделать несколько заказов — один за другим."
                ),
                new FaqItem(
                        "Как рассчитывается время аренды?",
                        "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. "
                                + "Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. "
                                + "Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."
                ),
                new FaqItem(
                        "Можно ли заказать самокат прямо на сегодня?",
                        "Только начиная с завтрашнего дня. Но скоро станем расторопнее."
                ),
                new FaqItem(
                        "Можно ли продлить заказ или вернуть самокат раньше?",
                        "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку "
                                + "по красивому номеру 1010."
                ),
                new FaqItem(
                        "Вы привозите зарядку вместе с самокатом?",
                        "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — "
                                + "даже если будете кататься без передышек и во сне. Зарядка не понадобится."
                ),
                new FaqItem(
                        "Можно ли отменить заказ?",
                        "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. "
                                + "Все же свои."
                ),
                new FaqItem(
                        "Я жизу за МКАДом, привезёте?",
                        "Да, обязательно. Всем самокатов! И Москве, и Московской области."
                )
        );
    }

    public static final class FaqItem {
        private final String question;
        private final String answer;

        public FaqItem(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }

        @Override
        public String toString() {
            return question;
        }
    }
}
