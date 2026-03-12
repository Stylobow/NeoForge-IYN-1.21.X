package fr.stylobow.iyc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import fr.stylobow.iyc.attachment.ModAttachmentTypes;
import fr.stylobow.iyc.network.SyncCosmeticPayload;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class CosmeticCommand {

    public static final List<String> VALID_COSMETICS = List.of("santa_hat");

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("cosmetic")
                .requires(source -> source.hasPermission(4))
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.argument("cosmeticId", StringArgumentType.word())
                                .suggests((context, builder) -> SharedSuggestionProvider.suggest(VALID_COSMETICS, builder))
                                .executes(context -> {
                                    ServerPlayer player = EntityArgument.getPlayer(context, "target");
                                    String cosmetic = StringArgumentType.getString(context, "cosmeticId");

                                    if (!VALID_COSMETICS.contains(cosmetic)) {
                                        context.getSource().sendFailure(Component.translatable("command.iyc.cosmetic.notfound", cosmetic));
                                        return 0;
                                    }

                                    for (ServerPlayer serverPlayer : context.getSource().getServer().getPlayerList().getPlayers()) {
                                        if (serverPlayer.getData(ModAttachmentTypes.ACTIVE_COSMETIC).equals(cosmetic)) {
                                            context.getSource().sendFailure(Component.translatable("command.iyc.cosmetic.already_worn", serverPlayer.getName().getString()));
                                            return 0;
                                        }
                                    }

                                    player.setData(ModAttachmentTypes.ACTIVE_COSMETIC, cosmetic);
                                    PacketDistributor.sendToAllPlayers(new SyncCosmeticPayload(player.getId(), cosmetic));
                                    context.getSource().sendSuccess(() -> Component.translatable("command.iyc.cosmetic.success", cosmetic, player.getName().getString()), true);
                                    return 1;
                                })
                        )
                        .then(Commands.literal("clear")
                                .executes(context -> {
                                    ServerPlayer player = EntityArgument.getPlayer(context, "target");

                                    if (player.getData(ModAttachmentTypes.ACTIVE_COSMETIC).isEmpty()) {
                                        context.getSource().sendFailure(Component.translatable("command.iyc.cosmetic.nocosmetic", player.getName().getString()));
                                        return 0;
                                    }

                                    player.setData(ModAttachmentTypes.ACTIVE_COSMETIC, "");
                                    PacketDistributor.sendToAllPlayers(new SyncCosmeticPayload(player.getId(), ""));
                                    context.getSource().sendSuccess(() -> Component.translatable("command.iyc.cosmetic.clear_success", player.getName().getString()), true);
                                    return 1;
                                })
                        )
                )
        );
    }
}