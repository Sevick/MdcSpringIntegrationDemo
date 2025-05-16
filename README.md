**Demo of the MDC usage with Spring Integration.**

# MdcSpringIntegrationDemo

A demonstration project integrating [Mapped Diagnostic Context (MDC)](https://logging.apache.org/log4j/2.x/manual/thread-context.html) with [Spring Integration](https://spring.io/projects/spring-integration). This project showcases how to propagate logging context information, such as trace IDs or user information, across asynchronous message flows using Spring Integration.

## Features

- Sample Spring Integration flow that reads console and process the string using pollable channel;
- Example configuration for MDC (log4j2) propagation within message flows.

## Usage

- The application starts a sample Spring Integration flow.
- Log output will include MDC data (e.g., correlation IDs) to demonstrate context propagation.
- Modify the provided flow or logging configuration to experiment with MDC in different scenarios.

## License

This project is licensed under the BSD License.