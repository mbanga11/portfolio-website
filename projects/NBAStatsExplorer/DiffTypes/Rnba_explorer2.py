from nba_api.stats.endpoints import leagueleaders, leaguedashplayerstats
import pandas as pd
from rich.console import Console
from rich.table import Table

console = Console()

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

def show_table(df, stat_category, mode):
    table = Table(title=f"Top 10 Players by {mode.capitalize()} {stat_category}")
    table.add_column("Rank", justify="right")
    table.add_column("Player", style="cyan")
    table.add_column("Team", style="magenta")
    table.add_column(stat_category, justify="right", style="green")

    top10 = df[['PLAYER', 'TEAM', stat_category]].head(10)
    for i, row in top10.iterrows():
        table.add_row(str(i), row['PLAYER'], row['TEAM'], f"{row[stat_category]:.2f}")
    console.print(table)

totals_df, averages_df = load_data(season, stat_category)

mode = "total"
current_df = totals_df.sort_values(by=stat_category, ascending=False)
current_df.index = range(1, len(current_df)+1)

show_table(current_df, stat_category, mode)

while True:
    search = input("\nEnter player name or team code (or 'exit', 'use avg', 'use total', or a stat like 'use ast'): ").strip()

    if search.lower() in ['exit', 'q']:
        print("Goodbye!")
        break

    if search.lower() == "use avg":
        mode = "average"
        current_df = averages_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        show_table(current_df, stat_category, mode)
        continue

    elif search.lower() == "use total":
        mode = "total"
        current_df = totals_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        show_table(current_df, stat_category, mode)
        continue

    elif search.lower() == "use pts":
        stat_category = "PTS"
    elif search.lower() == "use ast":
        stat_category = "AST"
    elif search.lower() == "use reb":
        stat_category = "REB"
    elif search.lower() == "use stl":
        stat_category = "STL"
    elif search.lower() == "use blk":
        stat_category = "BLK"
    elif search.lower() == "use tov":
        stat_category = "TOV"
    elif search.lower() == "use fg_pct":
        stat_category = "FG_PCT"
    elif search.lower() == "use fg3_pct":
        stat_category = "FG3_PCT"
    elif search.lower() == "use ft_pct":
        stat_category = "FT_PCT"
    elif search.lower() == "use plus_minus":
        stat_category = "PLUS_MINUS"
    elif search.lower() == "use min":
        stat_category = "MIN"
    elif search.lower() == "use eff":
        stat_category = "EFF"
    elif search.lower() == "use gp":
        stat_category = "GP"
        current_df = averages_df if mode == "average" else totals_df
        current_df = current_df.sort_values(by=stat_category, ascending=False)
        current_df.index = range(1, len(current_df)+1)
        show_table(current_df, stat_category, mode)
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
                console.print(f"\n📅 Switched to season {season}.")
                show_table(current_df, stat_category, mode)
            except:
                print("❌ Invalid season format. Use 'use season 2022-23'")
        else:
            print("❌ Usage: use season YYYY-YY (e.g., use season 2022-23)")
        continue

    current_df = averages_df if mode == "average" else totals_df
    current_df = current_df.sort_values(by=stat_category, ascending=False)
    current_df.index = range(1, len(current_df)+1)

    team_matches = current_df[current_df['TEAM'].str.upper() == search.upper()]
    if not team_matches.empty:
        console.print("\n📋 Matching Team Players:")
        console.print(team_matches[['PLAYER', 'TEAM', 'PTS', 'REB', 'AST']])
        continue

    player_matches = current_df[current_df['PLAYER'].str.contains(search, case=False)]
    if not player_matches.empty:
        console.print("\n📋 Matching Player(s):")
        console.print(player_matches[['PLAYER', 'TEAM', 'PTS', 'REB', 'AST']])
    else:
        print("\n❌ No matches found.")
