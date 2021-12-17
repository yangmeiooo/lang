package com.lenovo.relay.entity;

public class RelayRequest {

    private RelayCommand relayCommand;

    private String toguid;

    public RelayCommand getRelayCommand() {
        return relayCommand;
    }

    public String getToguid() {
        return toguid;
    }

    public void setRelayCommand(RelayCommand relayCommand) {
        this.relayCommand = relayCommand;
    }

    public void setToguid(String toguid) {
        this.toguid = toguid;
    }
}
