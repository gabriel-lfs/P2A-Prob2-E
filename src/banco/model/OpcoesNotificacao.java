/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banco.model;

/**
 *
 * @author guilh
 */
public enum OpcoesNotificacao {

    INATIVO {
        @Override
        public String toString() {
            return "Inativo";
        }
    },
    SMS {
        @Override
        public String toString() {
            return "SMS";
        }
    },
    WHATSAPP {
        @Override
        public String toString() {
            return "Mensagens WhatsApp";
        }
    },
    SMS_E_WHATSAPP {
        @Override
        public String toString() {
            return "WhatsApp e SMS";
        }
    },
    JMS {
        @Override
        public String toString() {
            return "JMS";
        }
    };

}
