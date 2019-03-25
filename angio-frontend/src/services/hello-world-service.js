import Vue from "vue";

export default class HelloWorldService {
  static _instance = new HelloWorldService();

  constructor() {
    if (HelloWorldService._instance) {
      throw new Error(
        "Instantiation failed: use Singleton.getInstance() instead of new."
      );
    }
  }

  /**
   * Return instance of service.
   * Usage example: helloWorldService = HellowWorldService.getInstance();
   */
  static getInstance() {
    return Singleton._instance;
  }

  /**
   * Returns 'Hello world!' string.
   */
  getHellowWorldMsg() {
    return "Hello world!";
  }

  /**
   * Return given message.
   *
   * @param  {string} msg message
   */
  getMsg(msg) {
    return msg;
  }
}
