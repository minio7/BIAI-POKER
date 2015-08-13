package biai.main.execute;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by I319033 on 2015-08-13.
 */
public class Main {

    private static NeuralNetLife neuralNetLife;

    public static void main(String[] args)
    {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-config.xml");
        neuralNetLife =(NeuralNetLife) context.getBean("program");
        neuralNetLife.executeNeuralNetLife();
    }
}
