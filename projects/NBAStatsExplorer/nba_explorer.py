from nba_api.stats.endpoints import leagueleaders, leaguedashplayerstats
import pandas as pd


season = "2024-25"
stat_category = "PTS"

def load_data(season, stat_category):
    totals = leagueleaders.LeagueLeaders(
        stat_category_abbreviation=stat_category,
        season=season
    ).get_data_frames()[0]

    avgs = leaguedashplayerstats.LeagueDashPlayerStats(
        per_mode_detailed='PerGame',
        season=season
    ).get_data_frames()[0]

    avgs = avgs.rename(columns={
        'PLAYER_NAME': 'PLAYER',
        'TEAM_ABBREVIATION': 'TEAM'
    })

    return totals, avgs

totals_df, averages_df = load_data(season, stat_category)

# Step 3: Initialize state
mode = "total"
current_df = totals_df.sort_values(by=stat_category, ascending=False)
current_df.index = range(1, len(current_df)+1)

# Step 4: Display initial top 10
print(f"\n🏀 Top 10 NBA Players by Total {stat_category}:")
print(current_df[['PLAYER', 'TEAM', 'PTS', 'REB', 'AST']].head(10))

# Step 5: Start loop
while True:
    search = input("""
Enter one of the following:

- Player name (e.g., LeBron James)
- 3-letter team code (e.g., LAL, BOS)
- 'use avg' or 'use total' to switch stat mode
- 'use [stat]' (e.g., use ast, use reb, use fg_pct, etc.)
- 'use season YYYY-YY' (e.g., use season 2023-24)
- 'exit' to quit

> """).strip()

    if search.lower() in ['exit', 'q']:
        print("Goodbye!")
        break

    if search.lower() == "use avg":
        mode = "average"
        current_df = averages_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\nSwitched to average (per-game) stats for {stat_category}:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use total":
        mode = "total"
        current_df = totals_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\nSwitched to total stats for {stat_category}:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use pts":
        stat_category = "PTS"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} {stat_category}:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use ast":
        stat_category = "AST"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Assists:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use reb":
        stat_category = "REB"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Rebounds:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use stl":
        stat_category = "STL"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Steals:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use blk":
        stat_category = "BLK"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Blocks:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use tov":
        stat_category = "TOV"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Turnovers:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use fg_pct":
        stat_category = "FG_PCT"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Field Goal %:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use fg3_pct":
        stat_category = "FG3_PCT"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} 3-Point %:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use ft_pct":
        stat_category = "FT_PCT"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Free Throw %:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use plus_minus":
        stat_category = "PLUS_MINUS"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📊 Top 10 Players by {mode.capitalize()} Plus/Minus:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use min":
        stat_category = "MIN"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n⏱️ Top 10 Players by {mode.capitalize()} Minutes:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use eff":
        stat_category = "EFF"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📈 Top 10 Players by {mode.capitalize()} Efficiency:")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower() == "use gp":
        stat_category = "GP"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        print(f"\n📈 Top 10 Players by {mode.capitalize()} Games Played :")
        print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
        continue

    elif search.lower().startswith("use season"):
        parts = search.split()
        if len(parts) == 3:
            new_season = parts[2]
            try:
                if "-" not in new_season:
                    raise ValueError
                season = new_season
                totals_df, averages_df = load_data(season, stat_category)
                current_df = averages_df if mode == "average" else totals_df
                current_df = current_df.sort_values(by=stat_category, ascending=False)
                current_df.index = range(1, len(current_df)+1)
                print(f"\n📅 Switched to season {season}.")
                print(current_df[['PLAYER', 'TEAM', stat_category]].head(10))
            except:
             print("❌ Invalid season format. Use 'use season 2022-23'")
        else:
            print("❌ Usage: use season YYYY-YY (e.g., use season 2022-23)")
        continue

    elif " vs " in search.lower():
        try:
            player1_name, player2_name = [name.strip() for name in search.split("vs")]
            df = averages_df if mode == "average" else totals_df

            p1 = df[df['PLAYER'].str.lower() == player1_name.lower()]
            p2 = df[df['PLAYER'].str.lower() == player2_name.lower()]

            if p1.empty or p2.empty:
                print("❌ One or both players not found.")
                continue

            stats_to_compare = ['PTS', 'REB', 'AST', 'STL', 'BLK', 'FG_PCT', 'TOV']
            p1_stats = p1[stats_to_compare].iloc[0]
            p2_stats = p2[stats_to_compare].iloc[0]

            print(f"\n📊 Comparing {player1_name} vs {player2_name}:\n")
            print(f"{'STAT':<10}{player1_name:<20}{player2_name:<20}{'Winner'}")

            p1_score = 0
            p2_score = 0

            for stat in stats_to_compare:
                p1_val = p1_stats[stat]
                p2_val = p2_stats[stat]

                if stat == 'TOV':
                    winner = player1_name if p1_val < p2_val else player2_name if p2_val < p1_val else "Tie"
                else:
                    winner = player1_name if p1_val > p2_val else player2_name if p2_val > p1_val else "Tie"

                if winner == player1_name:
                    p1_score += 1
                elif winner == player2_name:
                    p2_score += 1

                print(f"{stat:<10}{p1_val:<20.2f}{p2_val:<20.2f}{winner}")

            print(f"\n🏆 Final Result: {player1_name} ({p1_score}) vs {player2_name} ({p2_score})")

            if p1_score > p2_score:
                print(f"\n🎉 {player1_name} is the better player based on stats.")
            elif p2_score > p1_score:
                print(f"\n🎉 {player2_name} is the better player based on stats.")
            else:
                print("\n🤝 It's a tie!")
        except Exception as e:
            print("⚠️ Error comparing players. Please use format: Player1 vs Player2")
        continue

    # Team match
    team_matches = current_df[current_df['TEAM'].str.upper() == search.upper()]
    if not team_matches.empty:
        print("\n📋 Matching Team Players:")
        print(team_matches[['PLAYER', 'TEAM', 'PTS', 'REB', 'AST']])
        continue

    # Player match
    player_matches = current_df[current_df['PLAYER'].str.contains(search, case=False)]
    if not player_matches.empty:
        print("\n📋 Matching Player(s):")
        print(player_matches[['PLAYER', 'TEAM', 'PTS', 'REB', 'AST']])
    else:
        print("\n❌ No matches found.")
