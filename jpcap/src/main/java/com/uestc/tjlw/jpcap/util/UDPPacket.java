//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.uestc.tjlw.jpcap.util;

import java.io.Serializable;

import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.IPPort;
import net.sourceforge.jpcap.net.PacketEncoding;
import net.sourceforge.jpcap.net.UDPFields;
import net.sourceforge.jpcap.util.ArrayHelper;
import net.sourceforge.jpcap.util.Timeval;

public class UDPPacket extends IPPacket implements UDPFields, Serializable {
    private byte[] _udpHeaderBytes;
    private byte[] _udpDataBytes;
    private String _rcsid;

    public UDPPacket(int lLen, byte[] bytes) {
        super(lLen, bytes);
        this._udpHeaderBytes = null;
        this._udpDataBytes = null;
        this._rcsid = "$Id: UDPPacket.java,v 1.18 2004/05/05 23:14:45 pcharles Exp $";
    }

    public UDPPacket(int lLen, byte[] bytes, Timeval tv) {
        this(lLen, bytes);
        this._timeval = tv;
    }

    public int getSourcePort() {
        return ArrayHelper.extractInteger(this._bytes, this._ipOffset + 0, 2);
    }

    public int getDestinationPort() {
        return ArrayHelper.extractInteger(this._bytes, this._ipOffset + 2, 2);
    }

    public int getLength() {
        return ArrayHelper.extractInteger(this._bytes, this._ipOffset + 4, 2);
    }

    public int getUDPChecksum() {
        return ArrayHelper.extractInteger(this._bytes, this._ipOffset + 6, 2);
    }

    public int getChecksum() {
        return this.getUDPChecksum();
    }

    public byte[] getUDPHeader() {
        if (this._udpHeaderBytes == null) {
            this._udpHeaderBytes = PacketEncoding.extractHeader(this._ipOffset, 8, this._bytes);
        }

        return this._udpHeaderBytes;
    }

    public byte[] getHeader() {
        return this.getUDPHeader();
    }

    public byte[] getUDPData() {
        if (this._udpDataBytes == null) {
            int tmpLen = this._bytes.length - this._ipOffset - 8;
            this._udpDataBytes = PacketEncoding.extractData(this._ipOffset, 8, this._bytes, tmpLen);
        }

        return this._udpDataBytes;
    }

    public byte[] getData() {
        return this.getUDPData();
    }

    public byte[] getIpData(){
        return super.getData();
    }

    public String toString() {
        return this.toColoredString(false);
    }

    public String toColoredString(boolean colored) {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if (colored) {
            buffer.append(this.getColor());
        }

        buffer.append("UDPPacket");
        if (colored) {
            buffer.append("\u001b[0m");
        }

        buffer.append(": ");
        buffer.append(this.getSourceAddress());
        buffer.append('.');
        buffer.append(IPPort.getName(this.getSourcePort()));
        buffer.append(" -> ");
        buffer.append(this.getDestinationAddress());
        buffer.append('.');
        buffer.append(IPPort.getName(this.getDestinationPort()));
        buffer.append(" l=8," + (this.getLength() - 8));
        buffer.append(']');
        return buffer.toString();
    }

    public String getColor() {
        return "\u001b[1;32m";
    }
}
