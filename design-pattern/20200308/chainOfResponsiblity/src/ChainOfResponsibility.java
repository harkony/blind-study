package net.mossol.practice.design;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import lombok.extern.slf4j.Slf4j;

// GOF의 빌더패턴
@Slf4j
@RunWith(JUnit4.class)
public class ChainOfResponsibility {

    class StdoutLogger extends Logger {
        public StdoutLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.out.println("Writing to stdout: " + msg);
        }
    }

    class EmailLogger extends Logger {
        public EmailLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.out.println("Sending via email: " + msg);
        }
    }

    class StderrLogger extends Logger {
        public StderrLogger(int mask) {
            this.mask = mask;
        }

        protected void writeMessage(String msg) {
            System.err.println("Sending to stderr: " + msg);
        }
    }

    @Test
    public void test() {
        final Logger logger = new StdoutLogger(Logger.DEBUG);
        final Logger logger1 = logger.setNext(new EmailLogger(Logger.INFO));
        logger1.setNext(new StderrLogger(Logger.ERROR));

        logger.message("debug", Logger.DEBUG);
        logger.message("notice", Logger.INFO);
        logger.message("error", Logger.ERROR);
    }
}
