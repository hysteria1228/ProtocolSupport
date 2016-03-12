package protocolsupport.protocol.transformer.middlepacketimpl.clientbound.play.v_1_8;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ClientBoundPacket;
import protocolsupport.protocol.transformer.middlepacket.clientbound.play.MiddleBlockChangeMulti;
import protocolsupport.protocol.transformer.middlepacketimpl.PacketData;
import protocolsupport.protocol.typeremapper.id.IdRemapper;
import protocolsupport.protocol.typeremapper.id.RemappingTable;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public class BlockChangeMulti extends MiddleBlockChangeMulti<RecyclableCollection<PacketData>> {

	@Override
	public RecyclableCollection<PacketData> toData(ProtocolVersion version) throws IOException {
		RemappingTable remapper = IdRemapper.BLOCK.getTable(version);
		PacketData serializer = PacketData.create(ClientBoundPacket.PLAY_BLOCK_CHANGE_MULTI_ID, version);
		serializer.writeInt(chunkX);
		serializer.writeInt(chunkZ);
		serializer.writeVarInt(records.length);
		for (Record record : records) {
			serializer.writeShort(record.coord);
			serializer.writeVarInt(remapper.getRemap(record.id));
		}
		return RecyclableSingletonList.create(serializer);
	}

}
